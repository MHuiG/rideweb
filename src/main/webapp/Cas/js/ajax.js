window.onload = function () {
}

//formatTime(1545903266795, 'Y-M-D h:m:s')
function formatTime(number, format) {
    time = new Date(number)
    newArr = []
    formatArr = ['Y', 'M', 'D', 'h', 'm', 's']
    newArr.push(time.getFullYear())
    newArr.push(formatNumber(time.getMonth() + 1))
    newArr.push(formatNumber(time.getDate()))

    newArr.push(formatNumber(time.getHours()))
    newArr.push(formatNumber(time.getMinutes()))
    newArr.push(formatNumber(time.getSeconds()))

    for (i in newArr) {
        format = format.replace(formatArr[i], newArr[i])
    }
    return format;
}

function formatNumber(n) {
    n = n.toString()
    return n[1] ? n : '0' + n;
}

//用户信息
function Userstr(data) {
    var len = data.length;
    var str = '';
    var isroot = '';
    for (i = 0; i < len; i++) {
        if (data[i].isroot == "1") {
            isroot = "管理员"
        } else if (data[i].isroot == "0") {
            isroot = "游客"
        }
        str += '<div class="row" id="' + data[i].u_id + '">' +
            '<div class="col-xs-3">' +
            data[i].u_name +
            '</div>' +
            '<div class="col-xs-3">' +
            data[i].u_tel +
            '</div>' +
            '<div class="col-xs-3">' +
            isroot +
            '</div>' +
            '<div class="col-xs-3"><button class="btn btn-success btn-xs" data-toggle="modal" data-target="#reviseUser" onclick="editRecruit(this);">修改</button><button onclick="Deleteuser(this);" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deleteUser">删除</button></div>' +
            '</div>'
    }
    return str;
}

function showUser(data) {
    $("#userxinxi").html(Userstr(data));
}

//门票信息
function Ticketstr(data) {
    var len = data.length;
    var str = '';
    for (i = 0; i < len; i++) {
        if (data[i].t_status == "1") {
            status = "开售";
        } else {
            status = "停售";
        }
        str += '<div class="row" id="t' + data[i].t_id + '"><div class="col-xs-1">' +
            SelectSights_nameBySId(data[i].s_id, data[i].t_id) + data[i].s_id +
            '</div><div class="col-xs-1">' +
            data[i].t_category +
            '</div><div class="col-xs-1">' +
            data[i].t_price +
            '</div><div class="col-xs-1">' +
            status +
            '</div><div class="col-xs-1"><img  src="../main/Images/' + data[i].t_imgurl + '";></div><div class="col-xs-1">' +
            data[i].t_num +
            '</div><div class="col-xs-2">' +
            formatTime(data[i].t_time_start, 'Y-M-D') +
            '</div><div class="col-xs-2">' +
            formatTime(data[i].t_time_end, 'Y-M-D') +
            '</div><div class="col-xs-2"><button class="btn btn-success btn-xs" data-toggle="modal" data-target="#reviseTicket" onclick="editTickect(this);">修改</button><button  onclick="Deleteticket(this);" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deleteTicket">删除</button></div></div>'
    }
    return str;
}

function showTicket(data) {
    $("#ticketxinxi").html(Ticketstr(data));
}

//景点信息
function Sightstr(data) {
    var len = data.length;
    var str = '';
    for (i = 0; i < len; i++) {
        str += '<div class="row" id="s' + data[i].s_id + '"><div class="col-xs-2">' +
            data[i].s_name +
            '</div> <div class="col-xs-1" id="jingquzhaopian"><img  src="../main/Images/' + data[i].s_imgurl + '";></div><div class="col-xs-5">' +
            data[i].s_info.substring(0, 25) + '....' +
            '</div><div class="col-xs-2"><button class="btn btn-success btn-xs" data-toggle="modal" data-target="#reviseSight" onclick="editSight(this);">修改</button><button  onclick="Deletesight(this);" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deleteSight">删除</button></div></div>'
    }

    return str;
}

function showSight(data) {
    $("#jingquxinxi").html(Sightstr(data));
}

//订单信息
function Orderstr(data) {
    var len = data.length;
    var str = '';
    for (i = 0; i < len; i++) {
        if (data[i].order_state == "1") {
            order_state = "订票成功"
        } else {
            order_state = "退票成功"
        }
        if (data[i].pay_state == "1") {
            pay_state = "已支付"
        } else {
            pay_state = "未支付"
        }
        if (data[i].use_state == "1") {
            use_state = "已领票"
        } else {
            use_state = "未领票"
        }
        str += '<div class="row" id="o' + data[i].o_id + '"><div class="col-xs-2">' +
            data[i].o_id +
            '</div><div class="col-xs-2">' +
            SelectUseru_nameByUId(data[i].u_id, data[i].o_id) + data[i].u_id +
            '</div><div class="col-xs-2">' +
            SelectSightnameBySId(data[i].t_id, data[i].o_id) + data[i].t_id +
            '</div><div class="col-xs-1">' +
            data[i].o_num +
            '</div><div class="col-xs-1">' +
            order_state +
            '</div><div class="col-xs-1">' +
            pay_state +
            '</div><div class="col-xs-1">' +
            use_state +
            '</div><div class="col-xs-2"><button class="btn btn-success btn-xs" data-toggle="modal" data-target="#reviseOrder" onclick="editOrder(this);">修改</button><button  onclick="Deleteorder(this);" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deleteOrder">删除</button></div></div>'
    }

    return str;
}

function showOrder(data) {
    $("#dingdanxinxi").html(Orderstr(data));
}

//显示用户信息
function userxinxi() {
//	$.ajax({
//		url:"/ssm/GetAllUserList",
//		type :"POST",
//		dataType:"json",
//		success: function(data){
//			showUser(data);
//		},
//	    error : function(err) {
//	    	console.log(err);
//		}
//	});
    setTimeout(function () {
        $("#userxinxi").html('<div class="userlists"></div>');
        // 页数
        var page = 0;
        // 每页展示5个
        var size = 5;
        // dropload
        $('#userxinxi').dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var result = '';
                $.ajax({
                    url: "/ssm/UserInfo?currentPage=" + page + "&pageSize=" + size,
                    type: "POST",
                    dataType: "json",
                    success: function (data) {
                        data = data["lists"]
                        var arrLen = data.length;
                        if (arrLen > 0) {
                            for (var i = 0; i < arrLen; i++) {
                                if (data[i].isroot == "1") {
                                    isroot = "管理员"
                                } else if (data[i].isroot == "0") {
                                    isroot = "游客"
                                }
                                result += '<div class="row" id="' + data[i].u_id + '">' +
                                    '<div class="col-xs-3">' +
                                    data[i].u_name +
                                    '</div>' +
                                    '<div class="col-xs-3">' +
                                    data[i].u_tel +
                                    '</div>' +
                                    '<div class="col-xs-3">' +
                                    isroot +
                                    '</div>' +
                                    '<div class="col-xs-3"><button class="btn btn-success btn-xs" data-toggle="modal" data-target="#reviseUser" onclick="editRecruit(this);">修改</button><button onclick="Deleteuser(this);" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deleteUser">删除</button></div>' +
                                    '</div>'
                            }
                            // 如果没有数据
                        } else {
                            // 锁定
                            me.lock();
                            // 无数据
                            me.noData();
                        }
                        // 为了测试，延迟1秒加载
                        setTimeout(function () {
                            // 插入数据到页面，放到最后面
                            $('.userlists').append(result);
                            // 每次数据插入，必须重置
                            me.resetload();
                        }, 500);
                    },
                    error: function (xhr, type) {
                        console.log(err);
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                });
            }
        })
    }, 20);
}

//显示门票信息
function ticketxinxi() {
//$.ajax({
//	url:"/ssm/GetAllTicketList",
//	type :"POST",
//	dataType:"json",
//	success: function(data){
//		showTicket(data);
//		selectsnames();
//	},
//    error : function(err) {
//	console.log(err);
//}
//});
    setTimeout(function () {
        selectsnames();
        $("#ticketxinxi").html('<div class="ticketlists"></div>');
        // 页数
        var page = 0;
        // 每页展示5个
        var size = 5;
        // dropload
        $('#ticketxinxi').dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var result = '';
                $.ajax({
                    url: "/ssm/TicketInfo?currentPage=" + page + "&pageSize=" + size,
                    type: "POST",
                    dataType: "json",
                    success: function (data) {
                        data = data["lists"]
                        var arrLen = data.length;
                        if (arrLen > 0) {
                            for (var i = 0; i < arrLen; i++) {
                                if (data[i].t_status == "1") {
                                    status = "开售";
                                } else {
                                    status = "停售";
                                }
                                result += '<div class="row" id="t' + data[i].t_id + '"><div class="col-xs-1">' +
                                    SelectSights_nameBySId(data[i].s_id, data[i].t_id) + data[i].s_id +
                                    '</div><div class="col-xs-1">' +
                                    data[i].t_category +
                                    '</div><div class="col-xs-1">' +
                                    data[i].t_price +
                                    '</div><div class="col-xs-1">' +
                                    status +
                                    '</div><div class="col-xs-1"><img  src="../main/Images/' + data[i].t_imgurl + '";></div><div class="col-xs-1">' +
                                    data[i].t_num +
                                    '</div><div class="col-xs-2">' +
                                    formatTime(data[i].t_time_start, 'Y-M-D') +
                                    '</div><div class="col-xs-2">' +
                                    formatTime(data[i].t_time_end, 'Y-M-D') +
                                    '</div><div class="col-xs-2"><button class="btn btn-success btn-xs" data-toggle="modal" data-target="#reviseTicket" onclick="editTickect(this);">修改</button><button  onclick="Deleteticket(this);" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deleteTicket">删除</button></div></div>'

                            }
                            // 如果没有数据
                        } else {
                            // 锁定
                            me.lock();
                            // 无数据
                            me.noData();
                        }
                        // 为了测试，延迟1秒加载
                        setTimeout(function () {
                            // 插入数据到页面，放到最后面
                            $('.ticketlists').append(result);
                            // 每次数据插入，必须重置
                            me.resetload();
                        }, 500);
                    },
                    error: function (xhr, type) {
                        console.log(err);
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                });
            }
        });
    }, 20)
}

//显示景区信息
function jingquxinxi() {
//	$.ajax({
//		url:"/ssm/GetAllSightList",
//		type :"POST",
//		dataType:"json",
//		success: function(data){
//			showSight(data);
//		},
//	    error : function(err) {
//		console.log(err);
//	}
//	});
    setTimeout(function () {
        $("#jingquxinxi").html('<div class="sightlists"></div>');
        // 页数
        var page = 0;
        // 每页展示5个
        var size = 5;
        // dropload
        $('#jingquxinxi').dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var result = '';
                $.ajax({
                    url: "/ssm/SightInfo?currentPage=" + page + "&pageSize=" + size,
                    type: "POST",
                    dataType: "json",
                    success: function (data) {
                        data = data["lists"]
                        var arrLen = data.length;
                        if (arrLen > 0) {
                            for (i = 0; i < arrLen; i++) {
                                result += '<div class="row" id="s' + data[i].s_id + '"><div class="col-xs-2">' +
                                    data[i].s_name +
                                    '</div> <div class="col-xs-1" id="jingquzhaopian"><img  src="../main/Images/' + data[i].s_imgurl + '";></div><div class="col-xs-5">' +
                                    data[i].s_info.substring(0, 25) + '....' +
                                    '</div><div class="col-xs-2"><button class="btn btn-success btn-xs" data-toggle="modal" data-target="#reviseSight" onclick="editSight(this);">修改</button><button  onclick="Deletesight(this);" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deleteSight">删除</button></div></div>'

                            }
                            // 如果没有数据
                        } else {
                            // 锁定
                            me.lock();
                            // 无数据
                            me.noData();
                        }
                        // 为了测试，延迟1秒加载
                        setTimeout(function () {
                            // 插入数据到页面，放到最后面
                            $('.sightlists').append(result);
                            // 每次数据插入，必须重置
                            me.resetload();
                        }, 500);
                    },
                    error: function (xhr, type) {
                        console.log(err);
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                });
            }
        });
    }, 20)
}

//显示订单信息
function dingdanxinxi() {
//	$.ajax({
//		url:"/ssm/GetAllOrder",
//		type :"POST",
//		dataType:"json",
//		success: function(data){
//			showOrder(data);
//			selectunames();
//			selectsnames();
//		},
//	    error : function(err) {
//		console.log(err);
//	}
//	});

    setTimeout(function () {
        selectunames();
        selectsnames();
        $("#dingdanxinxi").html('<div class="orderlists"></div>');
        // 页数
        var page = 0;
        // 每页展示5个
        var size = 5;
        // dropload
        $('#dingdanxinxi').dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var result = '';
                $.ajax({
                    url: "/ssm/OrderInfo?currentPage=" + page + "&pageSize=" + size,
                    type: "POST",
                    dataType: "json",
                    success: function (data) {
                        data = data["lists"]
                        var arrLen = data.length;
                        if (arrLen > 0) {
                            for (i = 0; i < arrLen; i++) {
                                if (data[i].order_state == "1") {
                                    order_state = "订票成功"
                                } else {
                                    order_state = "退票成功"
                                }
                                if (data[i].pay_state == "1") {
                                    pay_state = "已支付"
                                } else {
                                    pay_state = "未支付"
                                }
                                if (data[i].use_state == "1") {
                                    use_state = "已领票"
                                } else {
                                    use_state = "未领票"
                                }
                                result += '<div class="row" id="o' + data[i].o_id + '"><div class="col-xs-2">' +
                                    data[i].o_id +
                                    '</div><div class="col-xs-2">' +
                                    SelectUseru_nameByUId(data[i].u_id, data[i].o_id) + data[i].u_id +
                                    '</div><div class="col-xs-2">' +
                                    SelectSightnameBySId(data[i].t_id, data[i].o_id) + data[i].t_id +
                                    '</div><div class="col-xs-1">' +
                                    data[i].o_num +
                                    '</div><div class="col-xs-1">' +
                                    order_state +
                                    '</div><div class="col-xs-1">' +
                                    pay_state +
                                    '</div><div class="col-xs-1">' +
                                    use_state +
                                    '</div><div class="col-xs-2"><button class="btn btn-success btn-xs" data-toggle="modal" data-target="#reviseOrder" onclick="editOrder(this);">修改</button><button  onclick="Deleteorder(this);" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deleteOrder">删除</button></div></div>'
                            }
                            // 如果没有数据
                        } else {
                            // 锁定
                            me.lock();
                            // 无数据
                            me.noData();
                        }
                        // 为了测试，延迟1秒加载
                        setTimeout(function () {
                            // 插入数据到页面，放到最后面
                            $('.orderlists').append(result);
                            // 每次数据插入，必须重置
                            me.resetload();
                        }, 500);
                    },
                    error: function (xhr, type) {
                        console.log(err);
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                });
            }
        });
    }, 20)
}

//查询景点s_nameBySId
function SelectSights_nameBySId(sid, tid) {
    setTimeout(function () {
        $.ajax({
            url: "/ssm/SelectSightBySId",
            type: "POST",
            dataType: "json",
            data: {'SId': sid},
            success: function (data) {
                //console.log(data);
                $("#t" + tid + "  > div:nth-child(1)").html(data.s_name);
            },
            error: function (err) {
                console.log(err);
            }
        });
    }, 500)

}

//查询用户u_nameByUId
function SelectUseru_nameByUId(uid, oid) {
    setTimeout(function () {
        $.ajax({
            url: "/ssm/SelectUserByUId",
            type: "POST",
            dataType: "json",
            data: {'UId': uid},
            success: function (data) {
                //console.log(data);
                $("#o" + oid + "  > div:nth-child(2)").html(data.u_name);
            },
            error: function (err) {
                console.log(err);
            }
        });
    }, 500)
}

//查询SelectSightnameBySId
function SelectSightnameBySId(sid, oid) {
    setTimeout(function () {
        $.ajax({
            url: "/ssm/SelectSightBySId",
            type: "POST",
            dataType: "json",
            data: {'SId': sid},
            success: function (data) {
                //console.log(data);
                $("#o" + oid + "  > div:nth-child(3)").html(data.s_name);
            },
            error: function (err) {
                console.log(err);
            }
        });
    }, 500)
}

//模糊查询用户
function SelectUser(str) {
    $.ajax({
        url: "/ssm/SelectUser",
        type: "POST",
        data: {'str': str},
        dataType: "json",
        success: function (data) {
            showUser(data);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//模糊查询门票
function SelectTicket(str) {
    $.ajax({
        url: "/ssm/SelectTicket",
        type: "POST",
        data: {'str': str},
        dataType: "json",
        success: function (data) {
            showTicket(data);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//模糊查询景区信息
function SelectSight(str) {
    $.ajax({
        url: "/ssm/SelectSight",
        type: "POST",
        data: {'str': str},
        dataType: "json",
        success: function (data) {
            showSight(data);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//模糊查询订单信息
function SelectOrder(str) {
    $.ajax({
        url: "/ssm/SelectOrder",
        type: "POST",
        data: {'str': str},
        dataType: "json",
        success: function (data) {
            showOrder(data);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//模糊查询用户
$("#SelectUser").click(function () {
    str = $("#userstr").val();
    console.log(str);
    SelectUser(str);
});
//模糊查询门票
$("#SelectTickect").click(function () {
    str = $("#tickectstr").val();
    console.log(str);
    SelectTicket(str);
});
//模糊查询景点
$("#SelectSight").click(function () {
    str = $("#sightstr").val();
    console.log(str);
    SelectSight(str);
});
//模糊查询订单
$("#SelectOrder").click(function () {
    str = $("#orderstr").val();
    console.log(str);
    SelectOrder(str);
});

//页面删除用户 记录id
function Deleteuser(obj) {
    id = obj.parentNode.parentNode.id;
    $("#uid").html(id);
}

//页面删除门票 记录id
function Deleteticket(obj) {
    id = obj.parentNode.parentNode.id.slice(1);
    $("#tid").html(id);
}

//页面删除景点 记录id
function Deletesight(obj) {
    id = obj.parentNode.parentNode.id.slice(1);
    $("#sid").html(id);
}

//页面删除订单 记录id
function Deleteorder(obj) {
    id = obj.parentNode.parentNode.id.slice(1);
    $("#oid").html(id);
}

//删除用户
$("#DeleteUserOK").click(function () {
    uid = $("#uid").html();
    $.ajax({
        url: "/ssm/DeleteUser",
        type: "POST",
        data: {'u_id': uid},
        dataType: "json",
        success: function (data) {
            if (data == "1") {
                userxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
});
//删除门票
$("#DeleteTickectOK").click(function () {
    tid = $("#tid").html();
    $.ajax({
        url: "/ssm/DeleteTicket",
        type: "POST",
        data: {'t_id': tid},
        dataType: "json",
        success: function (data) {
            if (data == "1") {
                ticketxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
});
//删除景点
$("#DeleteSightOK").click(function () {
    sid = $("#sid").html();
    $.ajax({
        url: "/ssm/DeleteSight",
        type: "POST",
        data: {'s_id': sid},
        dataType: "json",
        success: function (data) {
            if (data == "1") {
                jingquxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
});
//删除订单
$("#DeleteOrderOK").click(function () {
    oid = $("#oid").html();
    $.ajax({
        url: "/ssm/DeleteOrder",
        type: "POST",
        data: {'o_id': oid},
        dataType: "json",
        success: function (data) {
            if (data == "1") {
                dingdanxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
});
//增加用户
$("#adduserOK").click(function () {
    uName = $("#uName").val();
    uTel = $("#uTel").val();
    uPassword = $("#uPassword").val();
    isroot = $("#isroot").val();
    $.ajax({
        url: "/ssm/AddUser",
        type: "POST",
        dataType: "json",
        data: {
            'u_name': uName,
            'u_tel': uTel,
            'u_password': uPassword,
            'isroot': isroot
        },
        success: function (data) {
            console.log(data);
            if (data == "1") {
                userxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });

});

//增加Order
$("#addorderOK").click(function () {
    u_id = $("#uNameo").val();
    t_id = $("#tNameo").val();
    t_num = $("#tNum").val();
    $.ajax({
        url: "/ssm/AddOrder",
        type: "POST",
        dataType: "json",
        data: {
            'u_id': u_id,
            't_id': t_id,
            't_num': t_num,
        },
        success: function (data) {
            console.log(data);
            if (data == "1") {
                dingdanxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });

});

//添加门票 snames下拉列表
function selectsnames() {
    $.ajax({
        url: "/ssm/GetAllSightList",
        type: "POST",
        dataType: "json",
        success: function (data) {
            //console.log(data);
            var len = data.length;
            var str = '';
            for (i = 0; i < len; i++) {
                str += '<option value="' + data[i].s_id + '">' + data[i].s_name + '</option>';
            }
            $("#sName").html(str);
            $("#sName2").html(str);
            $("#tNameo").html(str);
            $("#tNameo2").html(str);
        },
        error: function (err) {
            console.log(err);
        }
    });
};

//添加order unames下拉列表
function selectunames() {
    $.ajax({
        url: "/ssm/GetAllUserList",
        type: "POST",
        dataType: "json",
        success: function (data) {
            //console.log(data);
            var len = data.length;
            var str = '';
            for (i = 0; i < len; i++) {
                str += '<option value="' + data[i].u_id + '">' + data[i].u_name + '</option>';
            }
            $("#uNameo").html(str);
            $("#uNameo2").html(str);
        },
        error: function (err) {
            console.log(err);
        }
    });
};
//增加门票
$("#addticketOK").click(function () {
    var formData = new FormData();
    formData.append('s_id', $("#sName").val());
    formData.append('t_price', $("#t_price").val());
    formData.append('t_status', $("#tStatus").val());
    formData.append('t_num', $("#t_num").val());
    formData.append('file', $("#tfile").get(0).files[0]);
    formData.append('t_time_start', $("#t_time_start").val());
    formData.append('t_time_end', $("#t_time_end").val());
    formData.append('t_category', $("#tCategory").val());
    $.ajax({
        url: "/ssm/AddTicketBySId",
        type: "POST",
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data == "1") {
                ticketxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });

});
//增加景点
$("#addsightOK").click(function () {
    var formData = new FormData();
    formData.append('s_name', $("#SName").val());
    formData.append('file', $("#sfile").get(0).files[0]);
    formData.append('s_info', $("#sinfo").val());
    $.ajax({
        url: "/ssm/AddSight",
        type: "POST",
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data == "1") {
                jingquxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });

});


//修改用户信息，显示原有信息
function editRecruit(div) {
    id = div.parentNode.parentNode.id;
    $.ajax({
        type: "post",
        url: "/ssm/SelectUserByUId",
        data: {"UId": id},
        dataType: "json",
        success: function (data) {
            if (data) {
                $("#ID").val(data.u_id);
                $("#xingming").val(data.u_name);
                $("#dianhua").val(data.u_tel);
                $("#mima").val("");
                //$("#mima").val(data.u_password);
                $("#quanxian").val(data.isroot);
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//修改门票信息，显示原有信息
function editTickect(div) {
    id = div.parentNode.parentNode.id.slice(1);
    $.ajax({
        type: "post",
        url: "/ssm/SelectTickectByTId",
        data: {"TId": id},
        dataType: "json",
        success: function (data) {
            if (data) {
                $("#Tid").val(data.t_id);
                $("#sName2").val(data.s_id);
                $("#t_price2").val(data.t_price);
                $("#tStatus2").val(data.t_status);
                $("#t_num2").val(data.t_num);
                $("#t_time_start2").val(formatTime(data.t_time_start, 'Y-M-D'));
                $("#t_time_end2").val(formatTime(data.t_time_end, 'Y-M-D'));
                $("#tCategory2").val(data.t_category);
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//修改景点信息，显示原有信息
function editSight(div) {
    id = div.parentNode.parentNode.id.slice(1);
    $.ajax({
        type: "post",
        url: "/ssm/SelectSightBySId",
        data: {"SId": id},
        dataType: "json",
        success: function (data) {
            //console.log(data);
            if (data) {
                $("#SIDs").val(data.s_id);
                $("#sName2s").val(data.s_name);
                $("#sinfo2").val(data.s_info);
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//修改Order信息，显示原有信息
function editOrder(div) {
    id = div.parentNode.parentNode.id.slice(1);
    $.ajax({
        type: "post",
        url: "/ssm/SelectOrderByOid",
        data: {"Oid": id},
        dataType: "json",
        success: function (data) {
            if (data) {
                $("#OID").val(data.o_id);
                $("#uNameo2").val(data.u_id);
                $("#tNameo2").val(data.t_id);
                $("#tNum2").val(data.o_num);
                $("#order_state2").val(data.order_state);
                $("#pay_state2").val(data.pay_state);
                $("#use_state2").val(data.use_state);
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//用户信息修改
function yonghuxiugai() {
    $.ajax({
        type: "post",
        url: "/ssm/UpdateUser",
        data: {
            "u_id": $("#ID").val(),
            "u_name": $("#xingming").val(),
            "u_tel": $("#dianhua").val(),
            "u_password": $("#mima").val(),
            "isroot": $("#quanxian").val()
        },
        dataType: "json",
        success: function (data) {
            if (data == "1") {
                userxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//Order信息修改
function EditOrder() {
    $.ajax({
        type: "post",
        url: "/ssm/UpdateOrder",
        data: {
            "o_id": $("#OID").val(),
            "u_id": $("#uNameo2").val(),
            "t_id": $("#tNameo2").val(),
            "o_num": $("#tNum2").val(),
            "order_state": $("#order_state2").val(),
            "pay_state": $("#pay_state2").val(),
            "use_state": $("#use_state2").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data == "1") {
                dingdanxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
}

//修改门票
function EditTickect() {
    var formData = new FormData();
    formData.append('t_id', $("#Tid").val());
    formData.append('s_id', $("#sName2").val());
    formData.append('t_price', $("#t_price2").val());
    formData.append('t_status', $("#tStatus2").val());
    formData.append('t_num', $("#t_num2").val());
    formData.append('file', $("#tfile2").get(0).files[0]);
    formData.append('t_time_start', $("#t_time_start2").val());
    formData.append('t_time_end', $("#t_time_end2").val());
    formData.append('t_category', $("#tCategory2").val());
    $.ajax({
        url: "/ssm/UpdateTicket",
        type: "POST",
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data == "1") {
                ticketxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });

};

//修改景点
function EditSight() {
    var formData = new FormData();
    formData.append('s_id', $("#SIDs").val());
    formData.append('s_name', $("#sName2s").val());
    formData.append('file', $("#sflie2").get(0).files[0]);
    formData.append('s_info', $("#sinfo2").val());
    $.ajax({
        url: "/ssm/UpdateSight",
        type: "POST",
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data == "1") {
                jingquxinxi();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });

};


function getChart1(xData, yData) {
    main = echarts.init(document.getElementById('main1'));
    option = {
        title: {
            text: '景点门票售出量统计',
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xData,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '门票售出量',
                type: 'bar',
                barWidth: '60%',
                data: yData
            }
        ]
    };
    main.setOption(option);
}

function getChart2(xData, yData) {
    main = echarts.init(document.getElementById('main2'));
    option = {
        title: {
            text: '营业额曲线图(天)',
        },
        tooltip: {
            trigger: 'axis',
        },
        xAxis: {
            type: 'category',
            data: xData,
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: yData,
            type: 'line',
            smooth: true
        }]
    };
    main.setOption(option);
}

//E
function Eshow() {
    var xData = [];
    var yData = [];
    $.ajax({
        url: "/ssm/getNum1",
        type: "POST",
        dataType: "json",
        success: function (data) {
            var length = data.length;
            for (var i = 0; i < length; i++) {
                if (data[i]['s_name'] !== null) {
                    xData.push(data[i]['s_name']);
                }
                if (data[i]['SUM(o_num)'] !== null) {
                    yData.push(data[i]['SUM(o_num)']);
                }
            }
            getChart1(xData, yData);
        },
        error: function (err) {
            console.log(err);
        }
    });
    var xData2 = [];
    var yData2 = [];

    $.ajax({
        url: "/ssm/getMan",
        type: "POST",
        dataType: "json",
        success: function (data) {
            var length = data.length;
            for (var i = 0; i < length; i++) {
                if (data[i]['order_time'] !== null) {
                    xData2.push(formatTime(data[i]['order_time'], 'Y-M-D'));
                }
                if (data[i]['man'] !== null) {
                    yData2.push(data[i]['man']);
                }
            }
            getChart2(xData2, yData2);
        },
        error: function (err) {
            console.log(err);
        }
    });

}

$.ajax({
    url: "/ssm/getKey",
    type: "POST",
    dataType: "json",
    success: function (data) {
        if (data) {
            console.log(data);
            $("#userName").html(data.u_name);
        }
    }
});

function logout() {
    $.ajax({
        url: "/ssm/logout",
        type: "POST",
        dataType: "json",
        success: function (data) {
            window.location.href = '/ssm/main/';
        }
    });
}

//function NextUser(){
//	$.ajax({
//		url:"/ssm/GetAllUserList",
//		type :"POST",
//		dataType:"json",
//		success: function(data){
//			showUser(data);
//		},
//	    error : function(err) {
//	    	console.log(err);
//		}
//	});
//}