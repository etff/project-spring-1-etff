import {combineReducers} from "redux";
import {connectRouter} from "connected-react-router";
import meetReducer from "./meetReducer";
import authReducer from "./authReducer";

const createRootReducer = (history) =>
  combineReducers({
      router: connectRouter(history),
      meet: meetReducer,
      auth: authReducer,
  });

export default createRootReducer;
