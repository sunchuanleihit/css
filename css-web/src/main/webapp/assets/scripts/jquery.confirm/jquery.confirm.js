(function ($) {

    $.confirm = function (params) {
        if ($(top.document).find('#confirmOverlay').length) {
            // A confirm is already shown on the page:
            return false;
        }

        var buttonHTML = '';
        $.each(params.buttons, function (name, obj) {

            // Generating the markup for the buttons:

            buttonHTML += '<a href="#" class="button ' + obj['class'] + '">' + name + '<span></span></a>';

            if (!obj.action) {
                obj.action = function () { };
            }
        });

        var markup = [
			'<div id="confirmOverlay">',
			'<div id="confirmBox">',
			'<h1>', params.title, '</h1>',
			'<p>', params.message, '</p>',
			'<div id="confirmButtons">',
			buttonHTML,
			'</div></div></div>'
		].join('');

        var bodyEl = $(top.document).find("BODY");

        $(markup).hide().appendTo(bodyEl).fadeIn();

        var buttons = $(top.document).find('#confirmBox .button');
        i = 0;

        buttons[buttons.length-1].focus();
        $.each(params.buttons, function (name, obj) {
            buttons.eq(i++).click(function () {

                // Calling the action attribute when a
                // click occurs, and hiding the confirm.

                $.confirm.hide();
                obj.action();
                return false;
            });
        });
    }

    $.confirm.hide = function () {
        //        $(top.document).find('#confirmOverlay').fadeOut(function () {
        //            $(this).remove();
        //        });
        $(top.document).find('#confirmOverlay').remove();
    }

    jAlert = function (message, title, callback) {
        if (message) { }

        if (!title) title = "消息";
        $.confirm({
            'title': title,
            'message': message,
            'buttons': {
                '确定': {
                    'class': 'blue',
                    'action': function () {
                        if (callback) {
                            callback();
                        }
                    }
                }
            }
        });
    }

    jConfirm = function (message, title, callback) {
        if (!title) title = "确认";
        $.confirm({
            'title': title,
            'message': message,
            'buttons': {
                '确定': {
                    'class': 'gray',
                    'action': function () {
                        if (callback) {
                            callback(true);
                        }
                    }
                },
                '取消': {
                    'class': 'blue',
                    'action': function () {
                        if (callback) {
                            callback(false);
                        }
                    }
                }
            }
        });
    };

    jYesNoConfirm = function (message, title, callback) {
        if (!title) title = "确认";
        $.confirm({
            'title': title,
            'message': message,
            'buttons': {
                '是': {
                    'class': 'gray',
                    'action': function () {
                        if (callback) {
                            callback(true);
                        }
                    }
                },
                '否': {
                    'class': 'blue',
                    'action': function () {
                        if (callback) {
                            callback(false);
                        }
                    }
                }
            }
        });
    };

    jPrompt = function (message, value, title, callback) {
        $.alerts.prompt(message, value, title, callback);
    };

})(jQuery);