import React, {useEffect, useState} from "react";
import {Breadcrumb, Layout, Space, Table} from "antd";
import {MEET_APPROVE_REQUEST, MEMBER_LOADING_REQUEST,} from "../../redux/types";
import {useDispatch, useSelector} from "react-redux";
import {Helmet} from "react-helmet";
import {Link} from "react-router-dom";
import "antd/dist/antd.css";

const {Content} = Layout;
const {Column, ColumnGroup} = Table;

const StudyApprove = (req) => {
  const {studies} = useSelector((state) => state.meet);
  const {memberId, isAuthenticated} = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const originData = [];
  const [data, setData] = useState(originData);
  const id = req.match.params.id;

  useEffect(() => {
    dispatch({
      type: MEET_APPROVE_REQUEST,
      payload: id,
    });
    dispatch({
      type: MEMBER_LOADING_REQUEST,
      payload: localStorage.getItem("token"),
    });
  }, [dispatch, id]);

  studies.map(({id, member, subject, status}) => {
    originData.push({
      key: id,
      name: member.name,
      subject,
      status,
    });
  });

  return (
      <>
        <Helmet title="Approval"/>

        <Content
            className="site-layout"
            style={{padding: "0 50px", marginTop: 30}}
        >
          <Breadcrumb style={{margin: "16px 0"}}>
            <Breadcrumb.Item>
              <h2>승인하기</h2>
            </Breadcrumb.Item>
          </Breadcrumb>
          <div
              className="site-layout-background"
              style={{padding: 24, minHeight: 380}}
          >
            <Table dataSource={originData}>
              <Column title="이름" dataIndex="name" key="name" ellipsis="true"/>
              <Column
                  title="공부주제"
                  dataIndex="subject"
                  key="subject"
                  ellipsis="true"
              />
              <Column
                  title="승인처리"
                  key="edit"
                  render={(text, record) => (
                      <Space size="middle">
                        {record.position !== "LEADER" ? (
                            <>
                              <Link to="#">승인</Link>
                              <Link to="#">거부</Link>
                            </>
                        ) : (
                            ""
                        )}
                      </Space>
                  )}
              />
            </Table>
          </div>
        </Content>
      </>
  );
};

export default StudyApprove;
