import store from "../../store";
import {MEMBER_LOADING_REQUEST} from "../../redux/types";

const loadUser = () => {
  try {
    store.dispatch({
      type: MEMBER_LOADING_REQUEST,
      payload: localStorage.getItem("token"),
    });
  } catch (e) {
    console.log(e);
  }
};

export default loadUser;
