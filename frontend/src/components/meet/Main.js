import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {MEET_LOADING_REQUEST} from "../../redux/types";

const Main = () => {
  const { meets } = useSelector((state) => state.meet);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch({ type: MEET_LOADING_REQUEST });
  }, [dispatch]);

  return <>Main</>;
};

export default Main;
