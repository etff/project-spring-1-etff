import React, {useEffect, useState} from "react";
import {Breadcrumb, Layout, message, Popconfirm, Space, Table} from "antd";
import {MEET_DELETE_REQUEST, MEET_MINE_LOADING_REQUEST,} from "../../redux/types";
import {useDispatch, useSelector} from "react-redux";
import {Helmet} from "react-helmet";
import {Link} from "react-router-dom";
import "antd/dist/antd.css";

const {Content} = Layout;
const {Column, ColumnGroup} = Table;

const MyStudy = () => {
  const {studies} = useSelector((state) => state.meet);
  const {memberId, isAuthenticated} = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const originData = [];
  const [data, setData] = useState(originData);

  useEffect(() => {
    dispatch({
      type: MEET_MINE_LOADING_REQUEST,
      payload: {
        memberId: localStorage.getItem("memberId"),
        token: localStorage.getItem("token"),
      },
    });
  }, [dispatch]);

  studies.map(({meetId, title, meetStatus, position, startedAt}) => {
    originData.push({
      key: meetId,
      startedAt,
      title,
      position,
      meetStatus,
    });
  });

  const handleDelete = (key) => {
    const newData = [...data];
    const index = newData.findIndex((item) => key === item.key);

    dispatch({
      type: MEET_DELETE_REQUEST,
      payload: {
        key,
        token: localStorage.getItem("token"),
      },
    });
    if (index > -1) {
      newData.splice(index, 1);
      setData(newData);
    } else {
      setData(newData);
    }
  };

  function cancel(e) {
    message.error("취소하였습니다");
  }

  console.log(data, "data");
  return (
      <>
        <Helmet title="MyStudy"/>

        <Content
            className="site-layout"
            style={{padding: "0 50px", marginTop: 30}}
        >
          <Breadcrumb style={{margin: "16px 0"}}>
            <Breadcrumb.Item>
              <h2>나의 모각코</h2>
            </Breadcrumb.Item>
          </Breadcrumb>
          <div
              className="site-layout-background"
              style={{padding: 24, minHeight: 380}}
          >
            <Table dataSource={data.length == 0 ? originData : data}>
              <Column
                  title="날짜"
                  dataIndex="startedAt"
                  key="startedAt"
                  ellipsis="true"
                  render={(text, record) =>
                      record.meetStatus === "OPEN" ? (
                          record.startedAt
                      ) : (
                          <del>{record.startedAt}</del>
                      )
                  }
              />
              <Column
                  title="제목"
                  dataIndex="title"
                  key="title"
                  ellipsis="true"
                  render={(text, record) =>
                      record.meetStatus === "OPEN" ? (
                          record.title
                      ) : (
                          <del>{record.title}</del>
                      )
                  }
              />
              <Column
                  title="변경하기"
                  key="edit"
                  render={(text, record) =>
                      record.meetStatus === "OPEN" ? (
                          <Space size="middle">
                            {record.position === "LEADER" ? (
                                <Popconfirm
                                    title="삭제하시겠습니까?"
                                    onConfirm={() => handleDelete(record.key)}
                                    onCancel={cancel}
                                    okText="예"
                                    cancelText="아니오"
                                >
                                  <Link to="#">삭제</Link>
                                </Popconfirm>
                            ) : (
                                ""
                            )}
                          </Space>
                      ) : (
                          ""
                      )
                  }
              />
            </Table>
          </div>
        </Content>
      </>
  );
};

export default MyStudy;
