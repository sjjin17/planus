const mapStore = {
  namespaced: true,
  state: {
    spotInfo: null,
  },
  getters: {},
  mutations: {
    SET_SPOT_INFO(state, payload) {
      state.spotInfo = payload;
    },
  },
};

export default mapStore;
