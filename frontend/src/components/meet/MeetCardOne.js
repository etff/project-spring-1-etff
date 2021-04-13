import {Card, Col} from "antd";
import {Link} from "react-router-dom";

const {Meta} = Card;

const MeetCardOne = ({meets}) => {
  return (
      <>
        {Array.isArray(meets)
            ? meets.map(({meetId, title, location, time, startedAt}) => {
              return (
                  <Col span={6} key={meetId}>
                    <Link to={`/meet/${meetId}`}>
                      <Card
                          cover={
                            <img
                                alt="study"
                                src="https://cdn.pixabay.com/photo/2021/03/18/19/56/keyboard-6105750__340.jpg"
                            />
                          }
                          actions={[location, time]}
                      >
                        <Meta title={title} description={startedAt}/>
                      </Card>
                    </Link>
                  </Col>
              );
            })
            : ""}
      </>
  );
};

export default MeetCardOne;
