
export default {
    namespaced: true,
    state: {
        token: '',
        username: ''
    },
    mutations: {
        setToken(state, token) {
            state.token = token;
        },
        clearToken(state) {
            state.token = '';
        },
        setUsername(state, username){
            state.username = username;
        }
    },
    getters: {
        isAuthenticated: state => {
            return state.token.length > 0;
        }
    }
}