//存放主要交互逻辑代码
//模块化
var seckill = {
    url : {
        now : function () {
            return '/seckill/time/now';
        },
        exposer : function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        kill : function (seckillId, md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },
    validatePhone : function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    handlerSeckillKill : function (seckillId, node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        console.log(seckillId);
        $.post(seckill.url.exposer(seckillId),{},function (data) {
            console.log(data);
            if(data && data['success']){
                var exposer = data['data'];
                if(exposer['exposed']){
                    var md5 = exposer['md5'];
                    var killUrl = seckill.url.kill(seckillId, md5);
                    $("#killBtn").one('click',function () {
                       // $(this).addClass('disabled');
                        $.post(killUrl, {}, function (data) {
                            console.log(data);
                            if(data && data['success']){
                                var result = data['data'];
                                var state = result['state'];
                                var stateInfo = result['stateInfo'];
                                console.log(stateInfo);
                                node.html(stateInfo);
                            }else{
                                var result = data['data'];
                                var stateInfo = result['stateInfo'];
                                node.html(stateInfo);
                            }
                        });
                    });
                    node.show();
                }else{
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countDown(seckillId,now,start,end);
                }
            }else{
                console.warn("data:"+data);
            }
        });
    },
    countDown : function (seckillId, nowTime, startTime, endTime) {
        console.log(seckillId);
        var $seckillBox = $("#seckill-box");
        if(nowTime >= endTime){
            $seckillBox.html('秒杀结束!');
        }else if(nowTime < startTime){
            var killTime = new Date(startTime + 1000);
            $seckillBox.countdown(killTime,function (e) {
                var format = e.strftime('秒杀计时: %D天 %H时 %M分 %S秒');
                $seckillBox.html(format);
            }).on('finish.countdown',function () {
                seckill.handlerSeckillKill(seckillId, $seckillBox);
            });

        }else{
            seckill.handlerSeckillKill(seckillId, $seckillBox);
        }
    },
    detail : {
        init : function (args) {
            var killPhone = $.cookie("killphone");
            if(!seckill.validatePhone(killPhone)){
                var $killPhoneModal = $("#killPhoneModal");
                $killPhoneModal.modal('show');
                $("#killPhoneBtn").click(function () {
                    var killPhone = $("#killPhoneKey").val();
                    if(seckill.validatePhone(killPhone)){
                        $.cookie("killphone",killPhone,{expires:7, path:"/seckill"});
                        window.location.reload();
                    }else{
                        $("#killPhoneMessage").css("display","inline-block");
                    }
                });
            }else{
                var id = args["seckillId"];
                var startTime = args["startTime"];
                var endTime = args["endTime"];
                $.get(seckill.url.now(),{},function (data) {
                    if(data && data['success']){
                        var now = data['data'];
                        seckill.countDown(id, now, startTime, endTime);
                    }else{
                        console.warn("data:"+data);
                    }
                });
            }
        }
    }
};
