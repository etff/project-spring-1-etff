import axios from "axios";
import {all, call, fork, put, takeEvery} from "redux-saga/effects";
import {push} from "connected-react-router";
import {
  CLEAR_ERROR_FAILURE,
  CLEAR_ERROR_REQUEST,
  CLEAR_ERROR_SUCCESS,
  REGISTER_FAILURE,
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
} from "../types";

// Register
const registerMemberAPI = (req) => {
  console.log(req, "req");

  return axios.post("api/v1/members", req);
};

function* registerMember(action) {
  try {
    const result = yield call(registerMemberAPI, action.payload);
    console.log(result, "RegisterMember Data");
    yield put({
      type: REGISTER_SUCCESS,
      payload: result.data,
    });
    yield put(push("/"));
  } catch (e) {
    yield put({
      type: REGISTER_FAILURE,
      payload: e.response,
    });
  }
}

function* watchRegisterMember() {
  yield takeEvery(REGISTER_REQUEST, registerMember);
}

// clear Error
function* clearError() {
  try {
    yield put({
      type: CLEAR_ERROR_SUCCESS,
    });
  } catch (e) {
    yield put({
      type: CLEAR_ERROR_FAILURE,
    });
    console.error(e);
  }
}

function* watchClearError() {
  yield takeEvery(CLEAR_ERROR_REQUEST, clearError);
}

export default function* authSaga() {
  yield all([fork(watchRegisterMember), fork(watchClearError)]);
}
