/**
 * ui库文件属性
 */
export default {
    value: [
        // {
        //     value: 0, label: "通用UI",
        //     children: [
        //         {
        //             value: 'basic',
        //             label: 'Basic',
        //             children: [{
        //                 value: 'cai-text',
        //                 type: 'inline-block',
        //                 wechat_tag:'<text></text>',
        //                 h5_tag:'<span style="padding: 3px 10px;">请输入文字</span>',
        //                 label: '文字'
        //             }, {
        //                 value: 'cai-rectangle',
        //                 type: 'block',
        //                 wechat_tag:'<view></view>',
        //                 h5_tag:'<div style="width:100%;height:112px;padding: 8px;"></div>',
        //                 label: '矩形'
        //             }, {
        //                 value: 'cai-cricle',
        //                 type: 'inline-block;',
        //                 wechat_tag:'<view></view>',
        //                 h5_tag:'<div style="border-radius:50%;width:128px;height:128px;background:#f1f1f1;"></div>',
        //                 label: '圆'
        //             }, {
        //                 value: 'cai-line',
        //                 type: 'inline-block;',
        //                 wechat_tag:'<view></view>',
        //                 h5_tag:'<svg class="shape shape-cmp" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xml:space="preserve" preserveAspectRatio="none meet" width="200" height="30" x="0" y="0" opacity="1" stroke="#eee" stroke-width="0" stroke-dasharray="none" viewBox="0 0 100 100"><path vector-effect="non-scaling-stroke" d="M100,51H0v-2h100V51z" fill="#ccc"></path></svg>',
        //                 label: '直线'
        //             }, {
        //                 value: 'cai-image',
        //                 type: 'inline-block;',
        //                 wechat_tag:`<view class="weui-media-box__hd weui-media-box__hd_in-appmsg">
        //                 <image class="weui-media-box__thumb" src="" background-size="cover" />
        //               </view>`,
        //                 h5_tag:'<div style="width:158px;height:95px;background:#f1f1f1;display: inline-block;vertical-align:top;"><image style="max-width:100%;height:100%;" src="" background-size="cover" /></div>',
        //                 label: '图片'
        //             }, {
        //                 value: 'cai-link',
        //                 type: 'inline-block',
        //                 wechat_tag:'<navigator url="#" class="weui-cell_link" >链接文字</navigator>',
        //                 h5_tag:'<a href="#" style="padding: 3px 8px;">链接文字</a>',
        //                 label: '链接区域'
        //             }]
        //         }
        //     ]
        // },
        {
            value: 1,
            label: "element-ui",
            children: [
                {
                    value: "basic",
                    label: "Basic",
                    children: [
                        {
                            value: "layout",
                            label: "Layout 布局",
                            html: `<el-row  :gutter="20">
                            <el-col :span="16"><div class="grid-content bg-purple"></div></el-col>
                            <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
                          </el-row>`,
                            css: `.bg-purple-dark {
                            background: #99a9bf;
                          }
                          .bg-purple {
                            background: #d3dce6;
                          }
                          .bg-purple-light {
                            background: #e5e9f2;
                          }
                          .grid-content {
                            border-radius: 4px;
                            min-height: 36px;
                          }`
                        },
                        {
                            value: "icon",
                            label: "Icon 图标",
                            html: `<i class="el-icon-delete"></i>`
                        },
                        {
                            value: "button",
                            label: "Button 按钮",
                            html: `<el-button type="primary">主要按钮</el-button>
                            <el-button type="success">成功按钮</el-button>`
                        }
                    ]
                },
                {
                    value: "form",
                    label: "Form",
                    children: [
                        {
                            value: "radio",
                            label: "Radio 单选框"
                        },
                        {
                            value: "checkbox",
                            label: "Checkbox 多选框"
                        },
                        {
                            value: "input",
                            label: "Input 输入框"
                        },
                        {
                            value: "input-number",
                            label: "InputNumber 计数器"
                        },
                        {
                            value: "select",
                            label: "Select 选择器"
                        },
                        {
                            value: "cascader",
                            label: "Cascader 级联选择器"
                        },
                        {
                            value: "switch",
                            label: "Switch 开关"
                        },
                        {
                            value: "slider",
                            label: "Slider 滑块"
                        },
                        {
                            value: "time-picker",
                            label: "TimePicker 时间选择器"
                        },
                        {
                            value: "date-picker",
                            label: "DatePicker 日期选择器"
                        },
                        {
                            value: "datetime-picker",
                            label: "DateTimePicker 日期时间选择器"
                        },
                        {
                            value: "upload",
                            label: "Upload 上传"
                        },
                        {
                            value: "rate",
                            label: "Rate 评分"
                        },
                        {
                            value: "form",
                            label: "Form 表单"
                        }
                    ]
                },
                {
                    value: "data",
                    label: "Data",
                    children: [
                        {
                            value: "table",
                            label: "Table 表格"
                        },
                        {
                            value: "tag",
                            label: "Tag 标签"
                        },
                        {
                            value: "progress",
                            label: "Progress 进度条"
                        },
                        {
                            value: "tree",
                            label: "Tree 树形控件"
                        },
                        {
                            value: "pagination",
                            label: "Pagination 分页"
                        },
                        {
                            value: "badge",
                            label: "Badge 标记"
                        }
                    ]
                },
                {
                    value: "notice",
                    label: "Notice",
                    children: [
                        {
                            value: "alert",
                            label: "Alert 警告"
                        },
                        {
                            value: "loading",
                            label: "Loading 加载"
                        },
                        {
                            value: "message",
                            label: "Message 消息提示"
                        },
                        {
                            value: "message-box",
                            label: "MessageBox 弹框"
                        },
                        {
                            value: "notification",
                            label: "Notification 通知"
                        }
                    ]
                },
                {
                    value: "navigation",
                    label: "Navigation",
                    children: [
                        {
                            value: "menu",
                            label: "NavMenu 导航菜单"
                        },
                        {
                            value: "tabs",
                            label: "Tabs 标签页"
                        },
                        {
                            value: "breadcrumb",
                            label: "Breadcrumb 面包屑"
                        },
                        {
                            value: "dropdown",
                            label: "Dropdown 下拉菜单"
                        },
                        {
                            value: "steps",
                            label: "Steps 步骤条"
                        }
                    ]
                },
                {
                    value: "others",
                    label: "Others",
                    children: [
                        {
                            value: "dialog",
                            label: "Dialog 对话框"
                        },
                        {
                            value: "tooltip",
                            label: "Tooltip 文字提示"
                        },
                        {
                            value: "popover",
                            label: "Popover 弹出框"
                        },
                        {
                            value: "card",
                            label: "Card 卡片"
                        },
                        {
                            value: "carousel",
                            label: "Carousel 走马灯"
                        },
                        {
                            value: "collapse",
                            label: "Collapse 折叠面板"
                        }
                    ]
                }
            ]
        },
        { value: 2, label: "we-ui" },
        { value: 3, label: "cube-ui" },
        { value: 4, label: "iWiew-ui" },
        { value: 5, label: "Mint-ui" },
    ]
}