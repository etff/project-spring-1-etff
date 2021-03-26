import axios from "axios";
import {all, call, fork, put, takeEvery} from "redux-saga/effects";
import {MEET_LOADING_FAILURE, MEET_LOADING_REQUEST, MEET_LOADING_SUCCESS,} from "../types";

const loadMeetAPI = (payload) => {
  return axios.get(`/api/v1/meets`);
};

function* loadMeets(action) {
  try {
    const result = yield call(loadMeetAPI, action.payload);
    console.log(result, "loadMeets");
    yield put({
      type: MEET_LOADING_SUCCESS,
      payload: result.data,
    });
  } catch (e) {
    yield put({
      type: MEET_LOADING_FAILURE,
      payload: e,
    });
  }
}

function* watchLoadMeets() {
  yield takeEvery(MEET_LOADING_REQUEST, loadMeets);
}

export default function* meetSaga() {
  yield all([fork(watchLoadMeets)]);
}
