import {
  CLEAR_ERROR_FAILURE,
  CLEAR_ERROR_REQUEST,
  CLEAR_ERROR_SUCCESS,
  MEET_LOADING_FAILURE,
  MEET_LOADING_REQUEST,
  MEET_LOADING_SUCCESS,
} from "../types";

const initialState = {
  isAuthenticated: null,
  meets: [],
  meetDetail: "",
  meetCount: "",
  loading: false,
  error: "",
  title: "",
  searchBy: "",
  searchResult: "",
  previousMatchMsg: "",
};

export default function (state = initialState, action) {
  switch (action.type) {
    case MEET_LOADING_REQUEST:
      return {
        ...state,
        loading: true,
      };
    case MEET_LOADING_SUCCESS:
      return {
        ...state,
        meets: [...state.meets, ...action.payload.content],
        meetCount: action.payload.totalElements,
        loading: false,
      };

    case MEET_LOADING_FAILURE:
      return {
        ...state,
        loading: false,
      };

    case CLEAR_ERROR_REQUEST:
      return {
        ...state,
        errorMsg: "",
      };
    case CLEAR_ERROR_SUCCESS:
      return {
        ...state,
        errorMsg: null,
      };
    case CLEAR_ERROR_FAILURE:
      return {
        ...state,
        errorMsg: null,
      };

    default:
      return state;
  }
}
