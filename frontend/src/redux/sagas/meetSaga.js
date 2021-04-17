import axios from "axios";
import {all, call, fork, put, takeEvery} from "redux-saga/effects";
import {
  MEET_APPROVE_FAILURE,
  MEET_APPROVE_REQUEST,
  MEET_APPROVE_SUCCESS,
  MEET_CREATE_FAILURE,
  MEET_CREATE_REQUEST,
  MEET_CREATE_SUCCESS,
  MEET_DELETE_FAILURE,
  MEET_DELETE_REQUEST,
  MEET_DELETE_SUCCESS,
  MEET_DETAIL_LOADING_FAILURE,
  MEET_DETAIL_LOADING_REQUEST,
  MEET_DETAIL_LOADING_SUCCESS,
  MEET_JOIN_FAILURE,
  MEET_JOIN_REQUEST,
  MEET_JOIN_SUCCESS,
  MEET_LOADING_FAILURE,
  MEET_LOADING_REQUEST,
  MEET_LOADING_SUCCESS,
  MEET_MINE_LOADING_FAILURE,
  MEET_MINE_LOADING_REQUEST,
  MEET_MINE_LOADING_SUCCESS,
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
      type: MEET_CREATE_SUCCESS,
      payload: result.data,
    });
    yield put(push(`/`));
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

// Meet Detail
const loadMeetDetailAPI = (payload) => {
  return axios.get(`/api/v1/meets/${payload}`);
};

function* loadMeetDetail(action) {
  try {
    const result = yield call(loadMeetDetailAPI, action.payload);
    yield put({
      type: MEET_DETAIL_LOADING_SUCCESS,
      payload: result.data,
    });
  } catch (e) {
    yield put({
      type: MEET_DETAIL_LOADING_FAILURE,
      payload: e,
    });
    console.log(e);
  }
}

function* watchLoadMeetDetail() {
  yield takeEvery(MEET_DETAIL_LOADING_REQUEST, loadMeetDetail);
}

// Meet Join
const joinMeetsAPI = (payload) => {
  const config = {
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  };
  const token = payload.token;
  if (token) {
    config.headers["Authorization"] = "Bearer " + token;
  }
  return axios.post(`/api/v1/meets/${payload.id}/join`, payload, config);
};

function* joinMeets(action) {
  try {
    const result = yield call(joinMeetsAPI, action.payload);
    yield put({
      type: MEET_JOIN_SUCCESS,
      payload: result.data,
    });
    yield put(push(`/meet/${action.payload.id}`));
  } catch (e) {
    yield put({
      type: MEET_JOIN_FAILURE,
      payload: e,
    });
    yield put(push(`/meet/${action.payload.id}`));
  }
}

function* watchJoinMeets() {
  yield takeEvery(MEET_JOIN_REQUEST, joinMeets);
}

// MEET mine
const myMeetsLoadingAPI = (payload) => {
  const config = {
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  };
  const token = payload.token;
  const id = payload.memberId;
  if (token) {
    config.headers["Authorization"] = "Bearer " + token;
  }
  return axios.get(`/api/v1/meets/join/${id}`, config);
};

function* myMeetsLoading(action) {
  try {
    const result = yield call(myMeetsLoadingAPI, action.payload);
    yield put({
      type: MEET_MINE_LOADING_SUCCESS,
      payload: result.data,
    });
    yield put(push(`/mystudy`));
  } catch (e) {
    yield put({
      type: MEET_MINE_LOADING_FAILURE,
      payload: e.response,
    });
    yield put(push(`/`));
  }
}

function* watchMyMeetsLoading() {
  yield takeEvery(MEET_MINE_LOADING_REQUEST, myMeetsLoading);
}

// MEET Delete
const deleteAPI = (payload) => {
  const config = {
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  };
  const token = payload.token;
  const id = payload.key;
  if (token) {
    config.headers["Authorization"] = "Bearer " + token;
  }
  return axios.delete(`/api/v1/meets/${id}`, config);
};

function* deleteMeet(action) {
  try {
    const result = yield call(deleteAPI, action.payload);
    yield put({
      type: MEET_DELETE_SUCCESS,
      payload: result.data,
    });
    yield put(push(`/mystudy`));
  } catch (e) {
    yield put({
      type: MEET_DELETE_FAILURE,
      payload: e.response,
    });
    yield put(push(`/`));
  }
}

function* watchDeleteMeet() {
  yield takeEvery(MEET_DELETE_REQUEST, deleteMeet);
}

// Meet Approve
const approveMeetDetailAPI = (payload) => {
  return axios.get(`/api/v1/meets/${payload}`);
};

function* approveMeetDetail(action) {
  try {
    const result = yield call(approveMeetDetailAPI, action.payload);
    yield put({
      type: MEET_APPROVE_SUCCESS,
      payload: result.data,
    });
  } catch (e) {
    yield put({
      type: MEET_APPROVE_FAILURE,
      payload: e,
    });
    console.log(e);
  }
}

function* watchApproveMeetDetail() {
  yield takeEvery(MEET_APPROVE_REQUEST, approveMeetDetail);
}

export default function* meetSaga() {
  yield all([
    fork(watchLoadMeets),
    fork(watchCreateMeets),
    fork(watchLoadMeetDetail),
    fork(watchJoinMeets),
    fork(watchMyMeetsLoading),
    fork(watchDeleteMeet),
    fork(watchApproveMeetDetail),
  ]);
}
