import React, {useState} from "react";
import {Button, Col, Form, Input, Modal, Row} from "antd";
import {useDispatch} from "react-redux";
import "antd/dist/antd.css";
import {MEET_JOIN_REQUEST} from "../../redux/types";

const CollectionCreateForm = ({visible, onCreate, onCancel}) => {
  const [form] = Form.useForm();
  const [buttonDisabled, setButtonDisabled] = useState(true);
  return (
      <Modal
          visible={visible}
          title="참가하기"
          okText="확인"
          cancelText="취소"
          onCancel={onCancel}
          onOk={() => {
            form
                .validateFields()
                .then((values) => {
                  form.resetFields();
                  onCreate(values);
                })
                .catch((info) => {
                  console.log("Validate Failed:", info);
                });
          }}
          okButtonProps={{disabled: buttonDisabled}}
      >
        <Form
            form={form}
            layout="vertical"
            name="apply"
            initialValues={{
              modifier: "public",
            }}
            onFieldsChange={() =>
                setButtonDisabled(
                    form.getFieldsError().some((field) => field.errors.length > 0)
                )
            }
        >
          <Form.Item
              name="time"
              label="참가시간"
              rules={[
                {
                  required: true,
                  message: "참가할 시간을 입력해주세요!",
                },
              ]}
          >
            <Input placeholder="ex) 00:00 ~ 14:00"/>
          </Form.Item>
          <Form.Item name="subject" label="공부주제">
            <Input/>
          </Form.Item>
        </Form>
      </Modal>
  );
};

const MeetJoin = ({meetDetail, memberId, isAuthenticated}) => {
  const dispatch = useDispatch();
  const [visible, setVisible] = useState(false);

  const contains = meetDetail.studies
      .map((v) => v.member.id)
      .includes(memberId);

  const buttonClick = () => {
    if (!isAuthenticated) {
      warning();
    } else {
      setVisible(true);
    }
  };

  const onCreate = (values) => {
    const {time, subject} = values;
    const token = localStorage.getItem("token");
    const id = meetDetail.meetId;
    const body = {time, subject, token, id};
    dispatch({
      type: MEET_JOIN_REQUEST,
      payload: body,
    });

    setVisible(false);
  };

  const warning = () => {
    Modal.warning({
      title: "로그인 해주세요",
      content: "로그인이 필요한 서비스입니다.",
      okText: "확인",
    });
  };

  return (
      <>
        <Row>
          <Col span={12}>
            <p>{meetDetail.message}</p>
          </Col>

          <Col span={11} push={1}>
            {!contains ? (
                <>
                  <Button type="primary" danger size="large" onClick={buttonClick}>
                    참가하기
                  </Button>
                  <CollectionCreateForm
                      visible={visible}
                      onCreate={onCreate}
                      onCancel={() => {
                        setVisible(false);
                      }}
                  />
                </>
            ) : (
                ""
            )}
          </Col>
        </Row>
      </>
  );
};

export default MeetJoin;
