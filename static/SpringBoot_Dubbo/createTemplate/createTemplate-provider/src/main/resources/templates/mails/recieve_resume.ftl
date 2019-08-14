<!DOCTYPE html>
<html>
<head>
    <title>接收简历</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
</head>
<body>
<div id="qm_con_body"><div id="mailContentContainer" class="qmbox qm_con_body_content" style="">
  <table cellpadding="0" cellspacing="0" border="0" width="640" style="margin:0 auto;color:#555; font:16px/26px '微软雅黑','宋体',Arail; "> 
   <tbody>
    <tr> 
     <td style="height:62px; background-color:#38ACD7; padding:10px 0 0 10px;"> <a href="${websiteUrl}" target="_blank"><img src="${logoUrl}" width="197" height="43" style="border:none;margin-left:10px;"></a> </td> 
    </tr> 
    <tr style="background-color:#fff;"> 
     <td style="padding:30px 38px;"> 
      <div>
       <span style="color:#38ACD7;"><span style="border-bottom:1px dashed #ccc;z-index:1" t="7" onclick="return false;"></span></span>，你好！
      </div> 
      <div style="margin:20px 0;word-wrap:break-word;">
     	${content}
      </div> 
      <div style="background:#fafafa;padding:10px 15px;"> 
       <h2 style="font-size:16px;font-weight:600;margin:0 0 5px 0;">${resumeName}</h2> 
       <div style="font-size:14px;">
        ${resumeContent} 
       </div> 
      </div> 
      <div style="margin-top:10px;padding:10px 15px;"> 
       <span style="color:#ff5e4e;">*</span>&nbsp;为保证数据安全，查看完整简历时请输入验证码：
       <span style="color:#ff5e4e;"><span style="border-bottom:1px dashed #ccc;z-index:1" t="7" onclick="return false;">${verifyCode}</span></span>
       <br> 
       <a href="${resumeUrl}" style="font-size:18px; color:#fff; background:#38ACD7;text-decoration:none;padding:5px 0px;display:block;width:130px;text-align:center;margin-top:10px;" target="_blank">查看完整简历</a> 
      </div> 
      <br>
      <div style="background:#fafafa;padding:10px 15px;"> 
       <h2 style="font-size:14px;font-weight:300;margin:0 0 5px 0;">该简历由：<font color="#019875"><a href="mailto:${forwardEmail}" target="_blank">${forwardEmail}</a></font>转发， <a href="${websiteUrl}" target="_blank" style="color:#38ACD7; text-decoration:underline;">${websiteName}</a>代发，<font color="red">请勿直接回复</font></h2> 
       <h2 style="font-size:14px;font-weight:300;margin:0 0 5px 0;"><a href="mailto:${forwardEmail}" target="_blank">回复请点击这里</a></h2> 
      </div> 
      <div style="font-size:12px; color:#999;line-height:20px;border-top:1px solid #e6e6e6;padding:10px 0;">
         如有任何问题，可以与我们联系，我们将尽快为你解答。
        <br> Email：<a href="mailto:${serviceEmail}" target="_blank">${serviceEmail}</a> ，电话：<span style="border-bottom:1px dashed #ccc;z-index:1" t="7" onclick="return false;" data="${serviceTel}">${serviceTel}</span>，QQ:<span style="border-bottom-width: 1px; border-bottom-style: dashed; border-bottom-color: rgb(204, 204, 204); z-index: 1; position: static;" t="7" onclick="return false;" isout="1">${serviceQQ}</span> 
       </div>
       </td> 
    </tr> 
    <tr> 
     <td style="line-height:30px;text-align:right;font-size:14px;"> 为保证邮箱正常接收，请将<a href="mailto:${serviceEmail}" target="_blank">${serviceEmail}</a>添加进你的通讯录</td> 
    </tr> 
   </tbody>
  </table>
</div></div>
</body>
</html>