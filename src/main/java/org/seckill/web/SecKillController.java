package org.seckill.web;

import org.seckill.domain.Seckill;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClose;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: 力子
 * @Description:
 * @Date: Created in 18:26 2016/11/1.
 */
@Controller
@RequestMapping("/seckill")
public class SecKillController {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService service;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list=service.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }
    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId")Long seckillId, Model model){
        if(seckillId==null)
            return "redirect:/seckill/list";
        Seckill seckill=service.getById(seckillId);
        if (seckill==null)
            return "forward:/seckill/list";
        model.addAttribute("seckill",seckill);
        return "detail";
    }
    //ajax
    //json
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody//告诉springmvc 为json  自己封装成json
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId){
        SeckillResult<Exposer> result;
        try{
            Exposer exposer=service.exportSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result =new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    /**
     *
     * @param seckillId
     * @param md5
     * @param phone
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<SeckillExecution> execution(@PathVariable("seckillId")Long seckillId,@PathVariable("md5")String md5,
                                                     @CookieValue(value = "killphone",required = false) Long phone){
        if(phone==null)
            return new SeckillResult<SeckillExecution>(false,"未注册");
        SeckillResult<Exposer> result;
        try {
//            SeckillExecution execution=service.excuteSeckill(seckillId,phone,md5);
            //存储过程掉哦用
            SeckillExecution execution=service.excuteSeckillPro(seckillId,phone,md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            SeckillExecution ex=new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false,ex);
        }

    }
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Date> date(){
        return new SeckillResult<Date>(true,new Date());
    }

}
