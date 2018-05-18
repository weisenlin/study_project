/**
 * description:
 *
 * @author baolin
 * @date 2017-12-20 15:24
 **/
Array.prototype.insert = function (index, item) {
    this.splice(index, 0, item);
};

Array.prototype.contains = function (obj, predicate) {
    var i = this.length;
    while (i--) {
        if (typeof predicate === "function") {
            if (predicate(this[i], obj)) {
                return true;
            }
        } else {
            if (this[i] == obj) {
                return true;
            }
        }
    }
    return false;
};

String.prototype.startWith = function (str) {
    var reg = new RegExp("^" + str);
    return reg.test(this);
};

String.prototype.endWith = function (str) {
    var reg = new RegExp(str + "$");
    return reg.test(this);
};

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

var nstUtils = {
    // 实现对象深度复制
    deepCopy: function (p, c) {
        if (!c) {
            if (p.constructor === Array) {
                c = [];
            } else {
                c = {};
            }
        }

        for (var i in p) {
            if (!p[i]) {
                c[i] = p[i];
            } else if (typeof p[i] === 'object') {
                c[i] = (p[i].constructor === Array) ? [] : {};
                nstUtils.deepCopy(p[i], c[i]);
            } else {
                c[i] = p[i];
            }
        }
        return c;
    },
    array: {
        isArray: function (arr) {
            return Object.prototype.toString.apply(arr) === "[object Array]";
        }
    },
    tree: {
        /**
         * 由树结构转化成普通数组结构
         *
         * @param childKey 字节点的名称
         * @param nodes 树
         * @returns {Array}
         */
        transformToArray: function (childKey, nodes, parent) {
            if (!nodes) return [];
            var r = [];
            if (nstUtils.array.isArray(nodes)) {
                for (var i = 0, l = nodes.length; i < l; i++) {
                    nodes[i].parent = parent;
                    r.push(nodes[i]);
                    if (nodes[i][childKey])
                        r = r.concat(nstUtils.tree.transformToArray(childKey, nodes[i][childKey]), nodes[i]);
                }
            } else {
                nodes.parent = parent;
                r.push(nodes);
                if (nodes[childKey])
                    r = r.concat(nstUtils.tree.transformToArray(childKey, nodes[childKey]), nodes);
            }
            return r;
        },
        getAllParent: function (node) {
            if (node.parent) {
                node.parent.open = true;
                nstUtils.tree.openRootParent(node.parent);
            }
        }
    },
    reset: function (jq) {
        if (!jq) {
            return;
        }
        var valueOld = $(jq).attr("valueOld");
        $(jq).val(valueOld);
        $(jq).focus();
        $(jq).select();
    },
    setValue: function (jq, value) {
        if (!jq) {
            return;
        }
        $(jq).val(value);
        $(jq).attr("valueOld", value);
    },
    date: {
        init: function (id) {
            var $date = $("#" + id);
            $date.bind("keyup afterpaste", function () {
                this.value = this.value.replace(/\D/g, '');
            });
            $date.keydown(function (event) {
                var keyCode = event.keyCode || event.which || event.charCode;
                if (keyCode !== 13) {
                    return;
                }
                var value = $date.val();
                nstUtils.date.checkDate(value, function (date) {
                    nstUtils.setValue($date, date);
                }, function () {
                    popup().error("格式错误：日期格式为：yyyyMMdd");
                    nstUtils.reset($date);
                });
            });
        },
        checkDate: function (value, success, error) {
            if (!value) {
                typeof success === "function" && success(value);
                return true;
            }
            var isValid = function (date) {
                return new Date(date).getDate() == date.substring(date.length - 2);
            };

            if (value.length === 8) {
                var date = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
            } else if (value.length === 10) {
                date = value;
            } else {
                typeof error === "function" && error();
                return false;
            }
            if (!isValid(date)) {
                typeof error === "function" && error(date);
                return false;
            }
            typeof success === "function" && success(date);
            return true;
        }
    }
};

//保留页面所有的combo 
var nst_combos = [];
var comboUtils = {
    typeSelect: function (id, onblur, onReset) {
        var $id = $("#" + id);
        var tabindex = $id.attr("tabindex");
        var name = $id.attr("name") || "";
        var disable = $id.attr("disable");
        var value = $id.attr("value");

        // noinspection JSPotentiallyInvalidConstructorUsage
        var myCombo = new dhtmlXComboFromSelect(id);
        //myCombo.setSkin("material");
        nst_combos.push(myCombo);
        disable ? myCombo.disable() : myCombo.enable();
        myCombo.setName(name);
        myCombo.setComboValue(value);
        myCombo.valueOld = value;
        if (!value) {
            myCombo.unSelectOption();
        }
        myCombo.enableFilteringMode(true);
        myCombo.enableAutocomplete();
        myCombo.setFilterHandler(function (mask, option) {
            // 左匹配
            var op = option;
            var label = op.text.split(" ")[1];
            if(label)
                label = label.toLocaleUpperCase();

            var value = op.text.split(" ")[0];
            if(value)
                value = value.toLocaleUpperCase();

            if(mask)
                mask = mask.toLocaleUpperCase();

            return value.indexOf(mask) === 0 || (label && label.indexOf(mask) === 0);
        });

        myCombo.attachEvent("onBlur", function () {
            // 取符合条件的第一个
            this.handle(this.getComboText());
        });

        $(myCombo.DOMelem_input).attr("tabindex", tabindex);

        myCombo.getValueIndexByText = function (text) {
            if (!text) return;
            var index = -1;
            for (var i = 0; i < this.getOptionsCount(); i++) {
                var op = this.getOptionByIndex(i);

                var label = op.text.split(" ")[1];
                if(label)
                    label = label.toLocaleUpperCase();

                var value = op.text.split(" ")[0];
                if(value)
                    value = value.toLocaleUpperCase();

                if(text)
                    text = text.toLocaleUpperCase();

                if (value.indexOf(text) === 0 || (label && label.indexOf(text) === 0) || op.text.toLocaleUpperCase() === text) {
                    index = i;
                    break;
                }
            }
            return index;
        };

        myCombo.focus = function () {
            $(myCombo.DOMelem_input).focus();
            $(myCombo.DOMelem_input).select();
        };

        myCombo.resetValue = function () {
            typeof onReset === "function" && onReset(myCombo);

            myCombo.setComboValue(this.valueOld);
            if (!this.valueOld) {
                myCombo.unSelectOption();
            }
            $(myCombo.DOMelem_input).focus();
            $(myCombo.DOMelem_input).select();
        };

        myCombo.setValue = function (value, index) {
            if (value !== 0 && index !== 0 && !value && !index) {
                myCombo.unSelectOption();
            }

            myCombo.setComboValue(value);
            if ((index && index !== -1) || index === 0) {
                myCombo.selectOption(index)
            }
            myCombo.valueOld = myCombo.getSelectedValue();
        };

        myCombo.handle = function (text) {
            var index = this.getValueIndexByText(text);
            if (index !== 0 && !index) {
                return;
            }

            if (index === -1) {
                this.resetValue();
                popup().confirmC("无法找到合适的匹配项", function () {
                    $(".nst-pop-confirm").modal('hide');
                    $(myCombo.DOMelem_input).focus();
                    $(myCombo.DOMelem_input).select();
                }, function () {
                    $(".nst-pop-confirm").modal('hide');
                    $(myCombo.DOMelem_input).focus();
                    $(myCombo.DOMelem_input).select();
                });
            } else {
                // 设置数据前置函数
                if (typeof myCombo.beforeSetValue === "function") {
                    var beforeResult = myCombo.beforeSetValue(myCombo.getOptionByIndex(index).value);
                    if (!beforeResult) {
                        // 前置函数返回false表示要阻止设置数据，重置
                        // this.resetValue();
                        // $(myCombo.DOMelem_input).focus();
                        // $(myCombo.DOMelem_input).select();
                        return myCombo;
                    }
                }

                if (myCombo.valueOld !== myCombo.getOptionByIndex(index).value) {
                    // 如果值发生了变化，调用onchange回调
                    typeof myCombo.onchange === "function" && myCombo.onchange();
                }

                myCombo.setValue(myCombo.getOptionByIndex(index).value, index);

                typeof onblur === "function" && onblur(myCombo);
            }
        };
        
      //初始化宽度查找父节点 td的宽度
//        myCombo.initSize = function(){
//        	var inputObj = this.getInput();
//        	var tdObject = $(inputObj).parents("td").first();
//        	if(tdObject)
//        		this.setSize(tdObject.width() - 5);
//        };

        return myCombo;
    },
    typeSelect_v2: function (id, data, onblur, onReset) {
        var $id = $("#" + id);
        var tabindex = $id.attr("tabindex");
        var name = $id.attr("name") || "";
        var disable = $id.attr("disable");
        var value = $id.attr("value");
        var valueInit = $id.attr("valueInit") || "";

        // noinspection JSPotentiallyInvalidConstructorUsage
        var myCombo = new dhtmlXComboFromSelect(id);
        //myCombo.setSkin("material");
        nst_combos.push(myCombo);
        disable ? myCombo.disable() : myCombo.enable();
        myCombo.setName(name);
        myCombo.setComboValue(value);
        myCombo.valueOld = value;
        myCombo.valueInit = valueInit;
        if (!value) {
            myCombo.unSelectOption();
        }
        myCombo.enableFilteringMode(false);
        myCombo.enableAutocomplete();
        myCombo.data = data;
        myCombo.getInput().combo = myCombo;
        $(myCombo.getInput()).keyup(function (event) {
            var keyCode = event.keyCode || event.which || event.charCode;
            // 38: 方向键上； 40: 方向键下
            if (keyCode === 38 || keyCode === 40) {
                this.combo.openSelect();
                return;
            }

            this.combo.initOptions(this.combo.getComboText());
            this.combo.openSelect();
        });

        //初始化数据
        myCombo.initOptions = function (text) {
            var mask = text;
            var ops = [];
            for (var i = 0; i < this.data.length; i++) {
                if (text) {
                    var label = this.data[i].text;
                    if(label)
                    	label = label.toLocaleUpperCase();
                    
                    var value = this.data[i].value;
                    if(value)
                    	value = value.toLocaleUpperCase();
                    
                    if(mask)
                    	mask = mask.toLocaleUpperCase();
                    
                    if (value.indexOf(mask) === 0 || (label.split(" ")[1] && label.split(" ")[1].indexOf(mask) === 0) || mask === label) {
                        ops.push(this.data[i]);
                    }
                } else {
                    ops.push(this.data[i]);
                }
                if (ops.length >= 15) {
                    break;//最多100行
                }
            }

            this.clearAll();
            this.addOption(ops);
        };


        myCombo.attachEvent("onBlur", function () {
            // 取符合条件的第一个
            this.handle(this.getComboText());
            typeof onblur === "function" && onblur(myCombo);
        });

        $(myCombo.DOMelem_input).attr("tabindex", tabindex);

        myCombo.getValueIndexByText = function (text) {
            if (!text) return;
            var index = -1;
            for (var i = 0; i < this.getOptionsCount(); i++) {
                var op = this.getOptionByIndex(i);
                
                var label = op.text.split(" ")[1];
                if(label)
                	label = label.toLocaleUpperCase();
                
                var value = op.text.split(" ")[0];
                if(value)
                	value = value.toLocaleUpperCase();
                
                if(text)
                	text = text.toLocaleUpperCase();
                
                
                
                if (value.indexOf(text) === 0 || (label && label.indexOf(text) === 0) || op.text.toLocaleUpperCase() === text) {
                    index = i;
                    break;
                }
            }
            return index;
        };

        myCombo.focus = function () {
            $(myCombo.DOMelem_input).focus();
            $(myCombo.DOMelem_input).select();
        };

        myCombo.resetValue = function () {
            typeof onReset === "function" && onReset(myCombo);

            myCombo.initOptions(this.valueOld);

            myCombo.setComboValue(this.valueOld);
            if (!this.valueOld) {
                myCombo.unSelectOption();
            }
            $(myCombo.DOMelem_input).focus();
            $(myCombo.DOMelem_input).select();
        };

        // 重置为初始化的值
        myCombo.resetInitValue = function () {
            myCombo.setValue(myCombo.valueInit);
        };

        myCombo.setValue = function (value, index) {
            if (value !== 0 && index !== 0 && !value && !index) {
                myCombo.unSelectOption();
            }

            myCombo.initOptions(value);

            myCombo.setComboValue(value);
            if ((index && index !== -1) || index === 0) {
                myCombo.selectOption(index)
            }
            myCombo.valueOld = myCombo.getSelectedValue();
        };

        myCombo.handle = function (text) {
            var index = this.getValueIndexByText(text);
            if (index !== 0 && !index) {
                return;
            }

            if (index === -1) {
                this.resetValue();
                popup().confirmC("无法找到合适的匹配项", function () {
                    //$(".nst-pop-confirm").modal('hide');
                    $(myCombo.DOMelem_input).focus();
                    $(myCombo.DOMelem_input).select();
                }, function () {
                   // $(".nst-pop-confirm").modal('hide');
                    $(myCombo.DOMelem_input).focus();
                    $(myCombo.DOMelem_input).select();
                });
            } else {
                // 设置数据前置函数
                if (typeof myCombo.beforeSetValue === "function") {
                    var beforeResult = myCombo.beforeSetValue(myCombo.getOptionByIndex(index).value);
                    if (!beforeResult) {
                        // 前置函数返回false表示要阻止设置数据，重置
                        // this.resetValue();
                        // $(myCombo.DOMelem_input).focus();
                        // $(myCombo.DOMelem_input).select();
                        return myCombo;
                    }
                }

                if (myCombo.valueOld !== myCombo.getOptionByIndex(index).value) {
                    // 如果值发生了变化，调用onchange回调
                    typeof myCombo.onchange === "function" && myCombo.onchange();
                }

                myCombo.setValue(myCombo.getOptionByIndex(index).value, index);
            }
        };
        
//        //初始化宽度查找父节点 td的宽度
//        myCombo.initSize = function(){
//        	var inputObj = this.getInput();
//        	var divObject = $(inputObj).parents("div").first();
//        	$(divObject).css('width', "100%");
//        };

        myCombo.initOptions(value);
        myCombo.handle(value);
        return myCombo;
    }
};

/**
 * 统一弹窗
 * eg:
 * popup().success("编辑成功");
 *
 * popup().error("参数不全");
 *
 * popup().info("保存成功");
 *
 * popup().confirm("确定禁用该角色吗？", function () {
 *      // 用户点确定
 *      popup().success("禁用成功");
 * },
 * function() {
 *      // 用户点取消
 * });
 */
var popup = function () {
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-top',
        theme: 'air'
    };

    return {
        error: function (msg) {
            msg = msg || "";
            // Messenger.options = {
            //     extraClasses: 'messenger-fixed messenger-on-top',
            //     theme: 'air'
            // };
            // Messenger().error({
            //     message: msg,
            //     showCloseButton: true,
            //     hideAfter: 3
            // });
            setTimeout(function(){
                xwaitopen_s(msg,'1','3');
            },50);
        },
        success: function (msg) {
            msg = msg || "";
            // Messenger.options = {
            //     extraClasses: 'messenger-fixed messenger-on-top',
            //     theme: 'air'
            // };
            // Messenger().success({
            //     message: msg,
            //     showCloseButton: true,
            //     hideAfter: 3
            // });
            setTimeout(function(){
                xwaitopen_s(msg,'1','2');
            },50);

        },
        info: function (msg) {
            msg = msg || "";
            // Messenger.options = {
            //     extraClasses: 'messenger-fixed messenger-on-top',
            //     theme: 'air'
            // };
            // Messenger().info({
            //     message: msg,
            //     showCloseButton: true,
            //     hideAfter: 3
            // });
            setTimeout(function(){
                xwaitopen_s(msg,'1','3');
            },50);
        },
        message: function (msg) {
            msg = msg || "";
            Messenger.options = {
                extraClasses: 'messenger-fixed messenger-on-top messenger-on-right',
                theme: 'air'
            };
            Messenger().info({
                message: msg,
                showCloseButton: true,
                hideAfter: 5
            });
        },
        confirm: function (message, confirm, cancel) {
            var $nst = $("#nst-pop-confirm");
            if ($nst.length > 0) {
                $("#nst-pop-confirm-btn").unbind();
                $("#nst-pop-confirm-cancel-btn").unbind();
                $("#nst-pop-confirm-message").text(message);
                $("#nst-pop-confirm-btn").click(function () {
                    typeof confirm === 'function' && confirm();
                    $nst.modal('hide');
                });
                $("#nst-pop-confirm-cancel-btn").click(function () {
                    typeof cancel === 'function' && cancel();
                    $nst.modal('hide');
                });
                $nst.modal('show');
            }

            // $nst.on('hide.bs.modal', function () {
            //     typeof cancel === 'function' && cancel();
            // })


            // message = message || "";
            // Messenger.options = {
            //     extraClasses: 'messenger-fixed messenger-on-top',
            //     theme: 'air'
            // };
            // var msg = Messenger().post({
            //     message: message,
            //     actions: {
            //         delete: {
            //             label: "确定",
            //             action: function () {
            //                 msg.hide();
            //                 typeof confirm === "function" && confirm();
            //             }
            //         },
            //         cancel: {
            //             label: "取消",
            //             action: function () {
            //                 msg.hide();
            //                 typeof cancel === "function" && cancel();
            //             }
            //         }
            //     }
            // });
        },
        confirmC: function (message, confirm, cancel) {
            alert(message);
            if(confirm)
                confirm();
        }
    }
};

jQuery.extend({
    /**
     * 显示Loading
     * eg: $.showLoading(); 不传参数默认Loading不消失，直到显示调用$.hideLoading();
     * 建议所有异步请求前都显示Loading
     * @param message 显示信息
     */
    showLoading: function (message) {
        // var $loading = $("#pop-wait");
        //
        // $loading.modal("show");
        // if (timeout) {
        //     setTimeout(function () {
        //         $loading.modal("hide");
        //         $("div").remove(".modal-backdrop")
        //     }, timeout);
        // }
        xwaitopen_s(message);
    },
    // 隐藏loading， eg：$.hideLoading();
    hideLoading: function () {
        // $("#pop-wait").modal("hide");
        // $("div").remove(".modal-backdrop")
        xwaitclose_s();
    }
});

$(function () {
    // 所有input获得焦点即全选
    $("input").focus(function () {
        $(this).select();
    });
    $("textarea").focus(function(){
        $(this).select();
    });
});