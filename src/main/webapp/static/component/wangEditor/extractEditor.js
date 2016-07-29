function extractWangEditor(element) {
    //==========wangEditor Start============
    var editor = new wangEditor(element);
    // 自定义菜单
    editor.config.menus = [
        'source',
        '|',
        'bold',
        'underline',
        'italic',
        'strikethrough',
        'forecolor',
        'bgcolor',
        'quote',
        'fontfamily',
        'fontsize',
        'head',
        'unorderlist',
        'orderlist',
        'link',
        'table',
        'img',
        'insertcode',
        '|',
        'fullscreen'
    ];
    editor.config.uploadImgUrl = path + "/upload/editor?token=" + token;
    editor.create();
    //==========wangEditor End============
}