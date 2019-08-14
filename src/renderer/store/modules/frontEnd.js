const state = {
    vue_logs: []
}

const mutations = {
    PUSH_LOG(state, payload) {
        state.vue_logs.push(payload)
    },
    CLEAR_LOGS(state, payload) {
        state.vue_logs = payload
    }
}

const actions = {
    pushLog({
        commit,
        state
    }, payload) {
        commit('PUSH_LOG', payload)
    },
    clearLogs({
        commit
    }, payload) {
        commit('CLEAR_LOGS', payload)
    }
}

export default {
    state,
    mutations,
    actions
}