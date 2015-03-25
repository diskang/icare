<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>无标题文档</title>
<link href="/resthouse/static/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/resthouse/static/css/manager.css" rel="stylesheet" type="text/css">
<!---------------------------------响应式------------------------>
<link href="/resthouse/static/css/conter.css" rel="stylesheet" type="text/css">
<link href="/resthouse/static/css/jeesite.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/resthouse/static/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/resthouse/static/css/easyui.css">
<link rel="stylesheet" type="text/css" href="/resthouse/static/css/icon.css">
</head>

<body>
<!-------------------------头部-------------------------->
<div class="head">
  <div class="container header-s">
    <div class="logo"><img src="/resthouse/static/images/logo.png" ></div>
    <div class="dianhua"><img src="/resthouse/static/images/d_1.png"></div>
  </div>
</div>
<!--------------------------导航条-------------------------------->
<div class="daohang">
  <div class="container Navigation">
    <ul id="topNavi">
      <li class="navli" href="#">首页</li>
    </ul>
  </div>
</div>


<div class="di">
  <div class="container Information">
  <!--------------------------左导航--------------------------------> 
    <div class="leftNav" id="leftNavi" >
      <div style="background:url('/resthouse/static/images/r_1.jpg');line-height:30px;font-size:16px;color: #fff;height:30px;padding-left:20px;font-size:20px;margin:0;text-align:left;">菜单</div>
      <ul id="lefttree" style="padding-left:20px;padding-top:10px;text-align:left;">
      </ul>
    </div>


  <!--------------------------右内容-------------------------------->
    <div class="rightNav" id="rightNavi" style="min-height:710px;">


    <!--------------------------老人列表-------------------------------->
      <div id="eldershow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">老人信息中心</div>
        <div class="old">
          <div class="page-header">老人信息查询:</div>
          <div class="Inquiry">
            <div class="form-group group">
              <label class="control" for="name">姓名:</label>
              <div class="col-smm-2">
                <input id="elder_name" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="col-ssm-1" for="name">房间:</label>
              <div class="col-smm-2">
                <input id="elder_bedid" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="col-smm-1" for="name">护理等级:</label>
              <div class="col-smm-2">
                <select id="elder_care_level" class="form-control"  value="">
                </select>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button id="elder-search" class="btn btn-default" onclick="elder.doSearch()" style="margin-left:30px;" >搜索</button>
              </div>
            </div>
          </div>
          <div class="list" style="min-height:500px">
              <table id="elderpage"  class="easyui-datagrid" title="老人信息列表" style="height:400px;" data-options="onDblClickRow:elder.onElderDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'elder_id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'bed_id',width:120,align:'center'">房 间</th>
                    <th data-options="field:'name',width:120,align:'center'">姓 名</th>
                    <th data-options="field:'identity_no',width:160,align:'center'">身份证号</th>
                    <th data-options="field:'gender',width:80,align:'center'">性 别</th>
                    <th data-options="field:'age',width:80,align:'center'">年 龄</th>
                    <th data-options="field:'care_level',width:120,align:'center'">护理等级</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>





      <!--------------------------员工列表-------------------------------->
      <div id="staffshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">员工信息中心</div>
        <div class="old">
          <div class="page-header">员工信息查询:</div>
          <div class="Inquiry">
            <div class="form-group group">
              <label class="control">姓名:</label>
              <div class="col-smm-2">
                <input id="elder_name" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="col-smm-1">角色:</label>
              <div class="col-smm-2">
                <select id="elder_care_level" class="form-control"  value="">
                </select>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button id="elder-search" class="btn btn-default" onclick="doSearch()" style="margin-left:30px;" >搜索</button>
              </div>
            </div>
          </div>
          <div class="list" style="min-height:500px">
              <table id="staffpage"  class="easyui-datagrid" title="员工信息列表" style="height:400px;" data-options="onDblClickRow:staff.onStaffDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'role',width:100,align:'center'">角色</th>
                    <th data-options="field:'name',width:100,align:'center'">姓 名</th>
                    <th data-options="field:'identity_no',width:160,align:'center'">身份证号</th>
                    <th data-options="field:'gender',width:80,align:'center'">性 别</th>
                    <th data-options="field:'phone',width:100,align:'center'">电 话</th>
                    <th data-options="field:'email',width:120,align:'center'">邮 箱</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>


      <!--------------------------排班-------------------------------->
      <div id="arrangeshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">员工排班</div>
        <div class="old">
          <div id="calendar" class="fc fc-ltr fc-unthemed">
            <div class="fc-toolbar">
              <div class="fc-left">
                <div class="fc-button-group">
                  <button type="button" class="fc-prev-button fc-state-default"><span class="fc-icon fc-icon-left-single-arrow"></span></button>
                  <button type="button" class="fc-next-button fc-state-default"><span class="fc-icon fc-icon-right-single-arrow"></span></button>
                </div>
                <button type="button" class="fc-today-button fc-state-default ">今天</button>
              </div>
              <div class="fc-center"><h2>员工排班记录</h2></div>
              <div class="fc-right">
                <button type="button" class="fc-allow-button fc-state-default ">排班</button>
                <button type="button" class="fc-submit-button fc-state-default ">提交</button>
              </div>
            </div>
            <div class="fc-view-container">
              <div class="fc-view" >
                <table style="width:800px;">
                  <thead >
                    <tr>
                      <th class="fc-name" >姓名</th>
                      <th class="fc-sun"></th>
                      <th class="fc-mon"></th>
                      <th class="fc-tue"></th>
                      <th class="fc-wed"></th>
                      <th class="fc-thu"></th>
                      <th class="fc-fri"></th>
                      <th class="fc-sat"></th>
                    </tr>
                  </thead>
                </table>
              </div>
              <div class="fc-view" style="max-height:470px;width:826px;overflow-y:scroll;" >
                <table>
                  <tbody class="fc-body">
                  </tbody>
                </table>  
              </div>
            </div>
          </div>
        </div>
      </div>



      <!--------------------------项目列表-------------------------------->
      <div id="itemshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">项目总表</div>
        <div class="old">
          <div class="list" style="min-height:280px;padding-top:10px;">
              <table id="careitempage"  class="easyui-datagrid" title="专护项目列表" style="height:270px;" data-options="onDblClickRow:item.onCareDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'care_level',width:100,align:'center'">护理等级</th>
                    <th data-options="field:'notes',width:300,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
          <div class="list" style="min-height:300px">
              <table id="areaitempage"  class="easyui-datagrid" title="房护项目列表" style="height:270px;" data-options="onDblClickRow:item.onAreaDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'notes',width:400,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>

      <!--------------------------院方项目列表-------------------------------->
      <div id="gerocareitemshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">养老院项目管理</div>
        <div class="old">
          <div class="list" style="min-height:280px;padding-top:10px;">
              <table id="gerocareitempage"  class="easyui-datagrid" title="专护项目列表" style="height:270px;" data-options="onDblClickRow:geroItem.onCareDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'level',width:100,align:'center'">护理等级</th>
                    <th data-options="field:'period',width:80,align:'center'">周期</th>
                    <th data-options="field:'frequency',width:80,align:'center'">频率</th>
                    <th data-options="field:'notes',width:300,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>
      <div id="geroareaitemshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">养老院项目管理</div>
        <div class="old">
          <div class="list" style="min-height:280px;padding-top:10px;">
              <table id="geroareaitempage"  class="easyui-datagrid" title="房护项目列表" style="height:270px;" data-options="onDblClickRow:geroItem.onCareDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'period',width:80,align:'center'">周期</th>
                    <th data-options="field:'frequency',width:80,align:'center'">频率</th>
                    <th data-options="field:'notes',width:300,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>

      <!--------------------------院方角色列表-------------------------------->
      <div id="geroroleshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">养老院角色管理</div>
        <div class="old">
          <div class="list" style="min-height:300px;padding-top:10px;">
              <table id="gerorolepage"  class="easyui-datagrid" title="角色列表" style="height:300px;width:500px;" data-options="onDblClickRow:role.onRoleDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">角色名</th>
                    <th data-options="field:'notes',width:300,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>



      <!--------------------------权限列表-------------------------------->
      <div id="authorityshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">权限中心</div>
        <div class="old">
          <div  id="authoritypanel" class="easyui-panel" title="权限列表" style="height:500px;width:500px;padding:10px;margin:0;overflow:scroll;" data-options="
            width:500,
            tools: [{ 
                iconCls: 'icon-add', 
                handler: function() { 
                  authority.addTreenode();
                } 
            }, '-',{ 
                iconCls: 'icon-remove', 
                handler: function(){ 
                  authority.delTreenode();
                }
            }]">
              <ul id="authoritytree" style="padding-left:20px;text-align:left;">
              </ul>
          </div>
          <div id="authorityInfo" class="hide">
              <li>name: <input type="text" id="pname" ></input></li>
              <li>permission: <input type="text" id="ppermission"></input></li>
              <li>href: <input type="text" id="phref"></input></li>
              <li>icon: <input type="text" id="picon"></input></li>
              <li>notes: <input type="text" id="pnotes"></input></li>
              <div class="col-md-offset-2">
                <button id="authoritybutton" class="btn btn-default" onclick="authority.buttonclk()" style="margin-left:180px;margin-top:10px;" >确定</button>
              </div>
          </div>
        </div>
      </div>


    </div><!--rightNav-->
  </div><!--inforamtion-->
</div><!--di-->

<!-----------------------老人个人信息------------------------------>
<div id="elder-dialog-form"  class="easyui-dialog" title="老人信息详情" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        toolbar: [{
          text:'修改',
          iconCls:'icon-edit',
          handler:function(){
            elder.editElderInfo();
          }
        }],
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            if(elder.method==='put' || elder.method==='post'  )
            {
              elder.buttonclk();
            }
            else $('#elder-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="elder-Info-card" class="info-card">
      <div id="elder-Info-card-a" class="info-card-a">
        <table>
          <tr><td class="td1"><text>老人姓名: </text></td><td class="td2"><input id="ename"class="easyui-validatebox textbox" data-options="required:true"></input>*</td></tr>
          <tr><td class="td1"><text>老人账号名: </text></td><td class="td2"><input id="eusername"class="easyui-validatebox textbox" data-options="required:true"></input>*</td></tr>
          <tr><td class="td1"><text>出生日期：</text></td><td class="td2"><input id="ebirthday"class="easyui-validatebox textbox" data-options="required:true,invalidMessage:'123',validType:'date'"></input>*</td></tr>
          <tr><td class="td1"><text>性    别：</text></td><td class="td2"><input id="egender"></input></td></tr>
          <tr><td class="td1"><text>民    族：</text></td><td class="td2"><input id="enationality"></input></td></tr>
          <tr><td class="td1"><text>籍    贯：</text></td><td class="td2"><input id="enative_place"></input></td></tr>
          <tr><td class="td1"><text>护理等级：</text></td><td class="td2"><input id="ecare_level"></input></td></tr>
          <tr><td class="td1"><text>户口所在地：</text></td><td class="td2"><input id="eresidence"></input></td></tr>
          <tr><td class="td1"><text>政治面貌：</text></td><td class="td2"><input id="epolitical_status"></input></td></tr>
          <tr><td class="td1"><text>教育水平：</text></td><td class="td2"><input id="eeducation"></input></td></tr>
          <tr><td class="td1"><text>身份证号：</text></td><td class="td2"><input id="eidentity_no"class="easyui-validatebox textbox" data-options="required:true,invalidMessage:'123',validType:'idcard'"></input></td></tr>
          <tr><td class="td1"><text>社保卡号：</text></td><td class="td2"><input id="enssf_id"></input></td></tr>
          <tr><td class="td1"><text>档案编号：</text></td><td class="td2"><input id="earchive_id"></input></td></tr>
          <tr><td class="td1"><text>入住床号：</text></td><td class="td2"><input id="earea_id" class="easyui-validatebox textbox" data-options="required:true"></input>*</td></tr>
          <tr><td class="td1"><text>联系人电话：</text></td><td class="td2"><input id="ephone_no"class="easyui-validatebox textbox" data-options="required:true,invalidMessage:'123',validType:'phoneNum'"></input>*</td></tr>
          <tr><td class="td1"><text>家庭地址：</text></td><td class="td2"><input id="eaddress"></input></td></tr>
        </table>
      </div>
      <div id="elder-Info-card-b" class="info-card-b"><img src="images/p_2.jpg"></div>
    </div>
</div>

<!-----------------------员工个人信息------------------------------>
<div id="staff-dialog-form"  class="easyui-dialog" title="员工信息详情" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        toolbar: [{
          text:'修改',
          iconCls:'icon-edit',
          handler:function(){
            staff.editStaffInfo();
          }
        }],
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            if(staff.method==='put' || staff.method==='post'  )
            {
              staff.buttonclk();
            }
            else $('#staff-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="staff-Info-card" class="info-card ">
      <div id="staff-Info-card-a" class="info-card-a">
        <table>
          <tr><td class="td1"><text>姓名: </text></td><td class="td2"><input id="sname"></input></td></tr>
          <tr><td class="td1"><text>电    话: </text></td><td class="td2"><input id="sphone" class="easyui-validatebox textbox" data-options="required:true,invalidMessage:'123',validType:'phoneNum'"></input></td></tr>
          <tr><td class="td1"><text>电子邮箱：</text></td><td class="td2"><input id="semail"></input></td></tr>
          <tr><td class="td1"><text>角    色：</text></td><td class="td2"><input id="srole_list"></input></td></tr>
          <tr><td class="td1"><text>出生日期：</text></td><td class="td2"><input id="sbirthday"></input></td></tr>
          <tr><td class="td1"><text>身份证号：</text></td><td class="td2"><input id="sidentity_no"class="easyui-validatebox textbox" data-options="required:true,invalidMessage:'123',validType:'idcard'"></input></td></tr>
          <tr><td class="td1"><text>性    别：</text></td><td class="td2"><input id="sgender"></input></td></tr>
          <tr><td class="td1"><text>户口所在地：</text></td><td class="td2"><input id="sresidence_address"></input></td></tr>
          <tr><td class="td1"><text>档案编号：</text></td><td class="td2"><input id="sarchive_id"></input></td></tr>
          <tr><td class="td1"><text>社保卡号：</text></td><td class="td2"><input id="snssf_id"></input></td></tr>
          <tr><td class="td1"><text>居住地址：</text></td><td class="td2"><input id="shousehold_address"></input></td></tr>
        </table>
      </div>
      <div id="staff-Info-card-b"class="info-card-b"><img src="images/p_2.jpg"></div>
    </div>
</div>

<!-----------------------角色权限信息------------------------------>
<div id="role-dialog-form"  class="easyui-dialog" title="角色信息" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            role.buttonclk();
            $('#role-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="authority-Info-card" class="info-card">
      <ul id="authoritychecktree" class="easyui-tree" style="padding-left:20px;text-align:left;" data-options="lines:true,checkbox:true">
      </ul>
    </div>    
</div>

<div id="rolepost-dialog-form"  class="easyui-dialog" title="角色信息" style="width:300px;height:200px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-200)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            role.postrole();
            $('#rolepost-dialog-form').dialog('close');
          }
        }]
      ">
      <p>角色名: <input type="text" id="rname" /></p>
      <p>说  明: <input type="text" id="rnotes" /></p>  
</div>


<script type="text/javascript" src="/resthouse/static/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" src="/resthouse/static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resthouse/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resthouse/static/js/topleftNavi.js"></script>
<script type="text/javascript" src="/resthouse/static/js/elder.js"></script>
<script type="text/javascript" src="/resthouse/static/js/staff.js"></script>
<script type="text/javascript" src="/resthouse/static/js/item.js"></script>
<script type="text/javascript" src="/resthouse/static/js/geroitem.js"></script>
<script type="text/javascript" src="/resthouse/static/js/role.js"></script>
<script type="text/javascript" src="/resthouse/static/js/authority.js"></script>
<script type="text/javascript" src="/resthouse/static/js/arrange.js"></script>
<script type="text/javascript" src="/resthouse/static/js/url.js"></script>
<script type="text/javascript" src="/resthouse/static/js/validbox.js"></script>
</body>
</html>
