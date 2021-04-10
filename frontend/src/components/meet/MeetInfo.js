import {Col, Image, Row, Typography} from "antd";

const {Title} = Typography;
const MeetInfo = ({meetDetail, memberId}) => {
  const leader = meetDetail.studies
      .filter((v) => v.position === "LEADER")
      .map((v) => v.member.name)[0];

  return (
      <>
        <Row>
          <Col span={12}>
            <Image src="https://cdn.pixabay.com/photo/2021/02/06/07/02/laptop-5987093_960_720.jpg"/>
          </Col>
          <Col span={11} push={1}>
            <Title>{meetDetail.title}</Title>
            <h2>
              {meetDetail.startedAt} {meetDetail.time}
            </h2>
            <h2>리더 : {leader}</h2>
            <h2>
              <a href="#">{meetDetail.location}</a>
            </h2>
            <h2>문의하기</h2>
          </Col>
        </Row>
      </>
  );
};

export default MeetInfo;
