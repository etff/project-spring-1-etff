import {Layout} from "antd";

const { Footer } = Layout;

const Foot = () => {
  const thisYear = () => {
    return new Date().getFullYear();
  };

  return (
    <Footer style={{ textAlign: "center" }}>
      Copyright &copy; <span>{thisYear()}</span>
    </Footer>
  );
};

export default Foot;
