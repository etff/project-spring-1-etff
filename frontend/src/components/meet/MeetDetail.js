import React, {useEffect} from "react";
import {Divider, Layout} from "antd";
import {useDispatch, useSelector} from "react-redux";
import {Helmet} from "react-helmet";
import "antd/dist/antd.css";
import {MEET_DETAIL_LOADING_REQUEST, MEMBER_LOADING_REQUEST,} from "../../redux/types";
import MeetInfo from "./MeetInfo";
import MeetJoin from "./MeetJoin";
import MeetAttendee from "./MeetAttendee";

const {Content} = Layout;

const MeetDetail = (req) => {
  const dispatch = useDispatch();
  const {memberId, isAuthenticated} = useSelector((state) => state.auth);

  const {meetDetail, title, loading} = useSelector((state) => state.meet);

  useEffect(() => {
    dispatch({
      type: MEET_DETAIL_LOADING_REQUEST,
      payload: req.match.params.id,
    });
    dispatch({
      type: MEMBER_LOADING_REQUEST,
      payload: localStorage.getItem("token"),
    });
  }, [dispatch, req.match.params.id]);

  return (
      <>
        <Helmet title="Details"/>
        <Content
            className="site-layout"
            style={{padding: "0 200px", marginTop: 30}}
        >
          <div
              className="site-layout-background"
              style={{padding: 24, minHeight: 380}}
          >
            {meetDetail && meetDetail.studies ? (
                <>
                  <MeetInfo meetDetail={meetDetail}/>
                  <Divider/>
                  <MeetJoin
                      meetDetail={meetDetail}
                      memberId={memberId}
                      isAuthenticated={isAuthenticated}
                  />
                  <Divider/>
                  <MeetAttendee meetDetail={meetDetail}/>
                </>
            ) : (
                <h1>no data</h1>
            )}
          </div>
        </Content>
      </>
  );
};

export default MeetDetail;
