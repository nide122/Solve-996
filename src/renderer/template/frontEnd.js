const exec = require('child_process').exec
const path = require('path')
const fs = require('fs')
// 子进程名称
let workerProcess
/**
 * 执行CMD
 * @param  {[type]} _store  [description]
 * @param  {[type]} cmdStr  [description]
 * @param  {[type]} cmdPath [description]
 * @return {[type]}         [description]
 */
export function runExec(_store, cmdStr, cmdPath) {
  // 执行命令行，如果命令不需要路径，或就是项目根目录，则不需要cwd参数：
  const __path = path.resolve(__dirname)
  workerProcess = exec(cmdStr, {
    cwd: cmdPath.length > 0 ? cmdPath : __dirname
  })
  _store.dispatch('pushLog', {
    type: 'info',
    log: 'Start : ' + cmdStr
  })
  // 不受child_process默认的缓冲区大小的使用方法，没参数也要写上{}：workerProcess = exec(cmdStr, {})

  // 打印正常的后台可执行程序输出
  workerProcess.stdout.on('data', function(data) {
    _store.dispatch('pushLog', {
      type: 'success',
      log: data
    })
  })

  // 打印错误的后台可执行程序输出
  workerProcess.stderr.on('data', function(data) {
    _store.dispatch('pushLog', {
      type: data.indexOf('warning') >= 0 ? 'warning' : 'danger',
      log: data
    })
  })

  // 退出之后的输出
  workerProcess.on('close', function(code) {
    _store.dispatch('pushLog', {
      type: 'info',
      log: code == 0 ? 'End : ' + code : 'End '
    })
  })
}

export function closeExec() {
  if (workerProcess && workerProcess.killed === false && workerProcess.exitCode != 0) { process.kill(workerProcess.pid) }
}
