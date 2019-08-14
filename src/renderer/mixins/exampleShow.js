import screenfull from "screenfull";
import ExampleShow from "@/components/ExampleShow";

export default {
    components: {
        ExampleShow
    },
    created() {
        //this.fetchCode();
    },
    data() {
        return {
            time: 0,
            code: "",
            collapseItemEls: [],
            isFullscreen: false,
            exampleShowHeight: 0,
            acitvedIds: {},
            codemirrorInstances: {},
            exampleShowMethos: {},
        }
    },
    watch: {
        isFullscreen(newVal) {
            if (newVal) {
                this.exampleShowHeight = window.screen.availHeight * 0.9
            } else {
                this.exampleShowHeight = ''
            }
        },
        dialogVisible(newVal) {
            !newVal && (this.codemirrorInstances = {})
        },

    },
    mounted() {},
    methods: {
        exampleShowReady(data) {
            this.codemirrorInstances[data.id] = data.codeMirrorEditorInstantce
            this.exampleShowMethos[data.id] = data.run

        },
        collapseChange(activeNames) { // 获取首次显示的索引
            //1.判断是否打开item
            if (activeNames.length > 0) {
                //2.获取最后一次打开的索引
                let lastAcitveIndex = activeNames[activeNames.length - 1]
                // console.log(lastAcitveIndex)
                //3.判断是否已打开过了，如果打开过了不在进行codemirror初始化刷新操作，否则对codemirror进行初始化刷新
                if (!this.acitvedIds[lastAcitveIndex]) {
                    // console.log(this.codemirrorInstances[lastAcitveIndex])
                    setTimeout(() => {
                        this.codemirrorInstances[lastAcitveIndex].refresh()
                        this.exampleShowMethos[lastAcitveIndex]()
                    }, 100)
                    // this.codemirrorInstances[lastAcitveIndex].refresh()
                    //4.将刷新后的索引进行缓存
                    this.acitvedIds[lastAcitveIndex] = lastAcitveIndex
                }

            }

        },
        screenFull(index) {
            let el = document.querySelectorAll('.m-example-show')[index]
            if (screenfull.enabled) {
                let instant = screenfull.toggle(el);
                let _this = this
                screenfull.on("change", () => {
                    _this.isFullscreen = screenfull.isFullscreen
                });
            } else {
                this.$message({
                    message: "默认浏览器不支持全屏状态",
                    type: "warning"
                });
            }
        },
        
    }
}