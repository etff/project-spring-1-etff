import axios from "axios";
import {all, call, fork, put, takeEvery} from "redux-saga/effects";
import {
  MEET_CREATE_FAILURE,
  MEET_CREATE_REQUEST,
  MEET_LOADING_FAILURE,
  MEET_LOADING_REQUEST,
  MEET_LOADING_SUCCESS,
} from "../types";
import {push} from "connected-react-router";

const loadMeetAPI = (payload) => {
  return axios.get(`/api/v1/meets`);
};

function* loadMeets(action) {
  try {
    const result = yield call(loadMeetAPI, action.payload);

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

// Meet Create

const createMeetsAPI = (payload) => {
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  const token = payload.token;
  if (token) {
    config.headers["Authorization"] = "Bearer " + token;
  }
  return axios.post("/api/v1/meets", payload, config);
};

function* createMeets(action) {
  try {
    const result = yield call(createMeetsAPI, action.payload);
    yield put({
      type: MEET_CREATE_FAILURE,
      payload: result.data,
    });
    yield put(push(`/meet/${result.data.id}`));
  } catch (e) {
    yield put({
      type: MEET_CREATE_FAILURE,
      payload: e,
    });
    yield put(push("/"));
  }
}

function* watchCreateMeets() {
  yield takeEvery(MEET_CREATE_REQUEST, createMeets);
}

export default function* meetSaga() {
  yield all([fork(watchLoadMeets), fork(watchCreateMeets)]);
}
