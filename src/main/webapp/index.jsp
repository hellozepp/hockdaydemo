<html>
<script type="text/javascript" src="js/jquery.form.js"></script>
<!-- 上传文件 -->
<script type="text/javascript" src="js/jquery-fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="js/jquery-fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="js/jquery-fileupload/jquery.xdr-transport.js"></script>
<script type="text/javascript" src="js/jquery-fileupload/jquery.fileupload.js"></script>
<body>
<h2>Hello World!</h2>
<form action="" method="POST" enctype="multipart/form-data">
    <input type="file" name="file" id="file" class="file_ipt list_files"/>
    <button type="button" class="fm_btns fm_btn_ok">提交</button>
</form>

</body>
<script>
    // $(".fm_btn_ok").click(function () {
    //     console.log("..............")
    //     $("#addForm").ajaxSubmit({
    //         url: "/test",
    //         data: data,
    //         type: "POST",
    //         dataType: "json",
    //         success: function (rs) {
    //             console.log("123123123 ")
    //         },
    //         error: function () {
    //             alert("提交异常!");
    //         }
    //     });
    // })

</script>
</html>
