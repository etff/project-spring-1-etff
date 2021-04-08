import {Button, Col, Row, Typography} from "antd";

const {Title} = Typography;
const MeetJoin = ({meetDetail, memberId}) => {
  const leaderId = meetDetail.studies
      .filter((v) => v.position === "LEADER")
      .map((v) => v.member.id)[0];

  return (
      <>
        <Row>
          <Col span={12}>
            <p>{meetDetail.message}</p>
          </Col>
          <Col span={11} push={1}>
            {leaderId !== memberId ? (
                <Button type="primary" danger size="large">
                  참가하기
                </Button>
            ) : (
                ""
            )}
          </Col>
        </Row>
      </>
  );
};

export default MeetJoin;
