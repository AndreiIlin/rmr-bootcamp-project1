import React, { FC } from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import { useGetUserReportsQuery } from '../../../store/api/reportsApiSlice/reportsApiSlice';
import ReportsContainerHeader from "../../../componenst/reportsContainer/reportsContainerHeader";

const UserReportsContainer: FC = () => {
  const { data } = useGetUserReportsQuery();
  return (
    <Container as="main" className="my-5 d-flex flex-column">
      <ReportsContainerHeader />
      {data?.length
        ? data.map((report) => (
            <Row className="w-100 border-bottom" key={report.title}>
              <Col xs={3} sm={8}>{report.title}</Col>
              <Col xs={3} sm={2}>{report.reportType}</Col>
              <Col xs={3} sm={2} className={report.reportStatus === 'REJECTED' ? 'text-danger' : 'text-warning'}>
                {report.reportStatus ? report.reportStatus.toLowerCase() : null}
              </Col>
            </Row>
          ))
        : null}
    </Container>
  );
};

export default UserReportsContainer;
