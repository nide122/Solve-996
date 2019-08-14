const getters = {
  sidebar: state => state.app.sidebar,
  language: state => state.app.language,
  device: state => state.app.device,
  permission_routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters
}
export default getters
