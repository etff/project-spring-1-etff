import {Card, Col} from "antd";
import {Link} from "react-router-dom";

const {Meta} = Card;

const PostCardOne = ({meets}) => {
  console.log(meets);
  return (
      <>
        {Array.isArray(meets)
            ? meets.map(({meetId, title, location, time, startedAt}) => {
              return (
                  <Col span={6} key={meetId}>
                    <Link to={`/meets/${meetId}`}>
                      <Card
                          cover={
                            <img
                                alt="example"
                                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
                            />
                          }
                      >
                        {startedAt}
                        <br/>
                        <h2>{title} </h2>
                        {location} {time}
                      </Card>
                    </Link>
                  </Col>
              );
            })
            : ""}
      </>
  );
};

export default PostCardOne;
