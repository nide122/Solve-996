import {
  app,
  shell,
  Menu,
  BrowserWindow,
  ipcMain
} from 'electron'
// 注意这个autoUpdater不是electron中的autoUpdater
import { autoUpdater } from "electron-updater"
// 更新服务器地址，比如"http://**.**.**.**:3002/download/"
// import {uploadUrl} from "../renderer/config/config";
// 检测更新，在你想要检查更新的时候执行，renderer事件触发后的操作自行编写
function updateHandle() {
  let message = {
    type:0,//0:检查更新出错 1:正在检查更新……  2:检测到新版本，正在下载…… 3:现在使用的就是最新版本，不用更新
    error: '检查更新出错',
    checking: '正在检查更新……',
    updateAva: '检测到新版本，正在下载……',
    updateNotAva: '现在使用的就是最新版本，不用更新',
  };
  const os = require('os');

  autoUpdater.setFeedURL('http://www.createsz.cn/download/');
  
  autoUpdater.on('error', function (error) {
    sendUpdateMessage(message.error,0)
  });
  autoUpdater.on('checking-for-update', function () {
    sendUpdateMessage(message.checking,1)
  });
  autoUpdater.on('update-available', function (info) {
    sendUpdateMessage(message.updateAva,2)
  });
  autoUpdater.on('update-not-available', function (info) {
    sendUpdateMessage(message.updateNotAva,3)
  });

  // 更新下载进度事件
  autoUpdater.on('download-progress', function (progressObj) {
    mainWindow.webContents.send('downloadProgress', progressObj)
  })
  autoUpdater.on('update-downloaded', function (event, releaseNotes, releaseName, releaseDate, updateUrl, quitAndUpdate) {

    ipcMain.on('isUpdateNow', (e, arg) => {
      console.log(arguments);
      console.log("开始更新");
      //some code here to handle event
      autoUpdater.quitAndInstall();
    });

    mainWindow.webContents.send('isUpdateNow')
  });

  ipcMain.on("checkForUpdate",()=>{
      //执行自动更新检查
      autoUpdater.checkForUpdates();
  })
}

// 通过main进程发送事件给renderer进程，提示更新信息
function sendUpdateMessage(text,type) {
  mainWindow.webContents.send('message', text,type)
}


/**
 * Set `__static` path to static files in production
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-static-assets.html
 */
if (process.env.NODE_ENV !== 'development') {
  global.__static = require('path').join(__dirname, '/static').replace(/\\/g, '\\\\')
}

let mainWindow
const winURL = process.env.NODE_ENV === 'development' ?
  `http://localhost:9080` :
  `file://${__dirname}/index.html`

function createWindow() {
  if (process.platform === 'darwin') {
    let template = [{
      label: "CREATE-AI",
      submenu: [{
        label: "关于我们",
        selector: "orderFrontStandardAboutPanel:"
      }, {
        type: "separator"
      }, {
        label: "退出",
        accelerator: "Command+Q",
        click: function() {
          app.quit();
        }
      }]
    }, {
      label: "修改",
      submenu: [{
        label: "撤销",
        role: 'undo'
      }, {
        label: "重做",
        role: 'redo'
      }, {
        type: 'separator'
      }, {
        label: "剪切",
        role: 'cut'
      }, {
        label: "复制",
        role: 'copy'
      }, {
        label: "粘贴",
        role: 'paste'
      }, {
        label: "删除",
        role: 'delete'
      }, {
        label: "全选",
        role: 'selectall'
      }]
    }, 
    // {
    //   label: '视图',
    //   submenu: [
    //   {
    //     label: "控制台",
    //     accelerator: process.platform === 'darwin' ? 'Alt+Command+I' : 'Ctrl+Shift+I',
    //     click(item, focusedWindow) {
    //       if (focusedWindow) {
    //         focusedWindow.webContents.toggleDevTools();
    //       }
    //     }
    //   }
    // ]
    // }, 
    {
      label: "窗口",
      role: 'window',
      submenu: [{
        label: "最小化",
        role: 'minimize'
      }, {
        label: "关闭",
        role: 'close'
      },{
        label: "刷新",
        accelerator: 'CmdOrCtrl+R',
        click(item, focusedWindow) {
          if (focusedWindow) focusedWindow.reload();
        }
      }, {
        label: "全屏",
        role: 'togglefullscreen'
      }]
    }, {
      role: 'help',
      // submenu: [{
      //   label: 'Shortcuts',
      //   accelerator: 'CmdOrCtrl+/',
      //   click() {
      //     options.openStaticWindow('help');
      //   }
      // }
      // , {
      //   label: 'Report an Issue',
      //   click() {
      //     shell.openExternal('https://github.com/stefanjudis/forrest/issues');
      //   }
      // }]
    }];
    Menu.setApplicationMenu(Menu.buildFromTemplate(template));
  } else {
    // Menu.setApplicationMenu(null)
    let template = [{
      label: "CREATE-AI",
      submenu: [ {
        label: "退出",
        accelerator: "Command+Q",
        click: function() {
          app.quit();
        }
      }]
    }, {
      label: "修改",
      submenu: [{
        label: "撤销",
        role: 'undo'
      }, {
        label: "重做",
        role: 'redo'
      }, {
        type: 'separator'
      }, {
        label: "剪切",
        role: 'cut'
      }, {
        label: "复制",
        role: 'copy'
      }, {
        label: "粘贴",
        role: 'paste'
      }, {
        label: "删除",
        role: 'delete'
      }, {
        label: "全选",
        role: 'selectall'
      }]
    }, 
    {
      label: "窗口",
      role: 'window',
      submenu: [{
        label: "最小化",
        role: 'minimize'
      },{
        label: "还原",
        role: 'resetzoom'
      }, {
        label: "关闭",
        role: 'close'
      },{
        label: "刷新",
        accelerator: 'CmdOrCtrl+R',
        click(item, focusedWindow) {
          if (focusedWindow) focusedWindow.reload();
        }
      }, {
        label: "全屏",
        role: 'togglefullscreen'
      }]
    }];
    Menu.setApplicationMenu(Menu.buildFromTemplate(template));
  }
  /**
   * Initial window options
   */
  mainWindow = new BrowserWindow({
    height: 563,
    useContentSize: true,
    width: 1000
  })
  // mainWindow.webContents.openDevTools();
  mainWindow.loadURL(winURL)
  mainWindow.on('closed', () => {
    mainWindow = null
  })
  updateHandle()
}

app.on('ready', createWindow)

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  if (mainWindow === null) {
    createWindow()
  }
})

/**
 * Auto Updater
 *
 * Uncomment the following code below and install `electron-updater` to
 * support auto updating. Code Signing with a valid certificate is required.
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-electron-builder.html#auto-updating
 */

/*
import { autoUpdater } from 'electron-updater'

autoUpdater.on('update-downloaded', () => {
  autoUpdater.quitAndInstall()
})

app.on('ready', () => {
  if (process.env.NODE_ENV === 'production') autoUpdater.checkForUpdates()
})
 */