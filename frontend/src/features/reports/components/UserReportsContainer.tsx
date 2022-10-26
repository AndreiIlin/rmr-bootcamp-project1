import React, { FC } from 'react';
import { Container } from 'react-bootstrap';
import { useGetUserReportsQuery } from '../../../store/api/reportsApiSlice/reportsApiSlice';

const UserReportsContainer: FC = () => {
  const { data } = useGetUserReportsQuery();
  return (
    <Container as="main" className="my-5 d-flex flex-column">
      {data?.length
        ? data.map((report) => (
            <div className="d-flex gap-5 w-100" key={report.title}>
              <p className="col-4">{report.title}</p>
              <p className="col-4">{report.contractId}</p>
              <div className="d-flex gap-3">
                <p>{report.reportType}</p>
                <p className={report.reportStatus === 'WAITING' ? 'text-warning' : 'text-success'}>
                  {report.reportStatus ? report.reportStatus.toLowerCase() : null}
                </p>
              </div>
            </div>
          ))
        : null}
    </Container>
  );
};

export default UserReportsContainer;
