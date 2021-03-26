import {combineReducers} from "redux";
import {connectRouter} from "connected-react-router";
import meetReducer from "./meetReducer";

const createRootReducer = (history) =>
  combineReducers({
    router: connectRouter(history),
    meet: meetReducer,
  });

export default createRootReducer;
