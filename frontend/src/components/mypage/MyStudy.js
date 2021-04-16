import {Breadcrumb, Layout, Space, Table} from "antd";
import {Helmet} from "react-helmet";
import "antd/dist/antd.css";
import {useDispatch} from "react-redux";

const {Content} = Layout;
const {Column, ColumnGroup} = Table;

const MyStudy = () => {
  //const {meets} = useSelector((state) => state.meet);
  const dispatch = useDispatch();

  // const {isAuthenticated} = useSelector((state) => state.auth);

  // useEffect(() => {
  //     dispatch({type: MEET_LOADING_REQUEST});
  // }, [dispatch]);

  const data = [
    {
      key: "1",
      startedAt: "2020-04-02",
      title: "강남에서 모각코해요",
    },
    {
      key: "2",
      startedAt: "2020-04-21",
      title: "홍대에서 모각코해요",
    },
    {
      key: "3",
      startedAt: "2020-05-21",
      title: "종로에서 모각코해요",
    },
  ];

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
            <Table dataSource={data}>
              <Column
                  title="날짜"
                  dataIndex="startedAt"
                  key="startedAt"
                  ellipsis="true"
              />
              <Column
                  title="제목"
                  dataIndex="title"
                  key="title"
                  ellipsis="true"
              />
              <Column
                  title="변경하기"
                  key="edit"
                  render={(text, record) => (
                      <Space size="middle">
                        <a>수정</a>
                        <a>삭제</a>
                      </Space>
                  )}
              />
            </Table>
            ,
          </div>
        </Content>
      </>
  );
};

export default MyStudy;
