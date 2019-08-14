<!DOCTYPE html>
<html>
<head>
    <title>待沟通</title>
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
        	<td style="padding:10px 38px;">
        		<div style="margin:20px 0;"></div>
                    应聘公司：
                   <a href="${companyUrl}" target="_blank" style=" text-decoration:underline;">${companyName}</a>
                   <br> 应聘职位：
                   <a href="${postUrl}" target="_blank" style=" text-decoration:underline;">${postName}</a>
				    <br> 简历状态：${resumeStatus}<br>
        		   <div style="height:10px; border-bottom:1px solid #e6e6e6; margin-top:10px; margin-bottom:10px;"></div>
                	${feedback}
                    <br><br>
                    同时，提醒您注意保持简历中电话、邮箱等联系方式的畅通。
                    <br><br>
                    希望您能有机会加入我们，与我们一起共同成长。
                    <br><br>
                <div style="color:#c5c5c5; font-size:14px; border-top:1px solid #e6e6e6; padding:7px 0; line-height:20px;">
         本邮件由企业操作，
        <a href="${websiteUrl}" target="_blank" style="color:#38ACD7; text-decoration:underline;">${websiteName}</a>代发，请勿直接回复 
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
    </tbody></table>


</div></div>
</body>
</html>