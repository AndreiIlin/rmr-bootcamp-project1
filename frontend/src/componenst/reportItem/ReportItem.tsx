import classNames from 'classnames';
import React, { FC } from 'react';
import { Col, Row } from 'react-bootstrap';
import { useAppDispatch } from '../../hooks/defaultHooks';
import { Report } from '../../store/api/reportsApiSlice/reportsApiSlice';
import { openModal } from '../../store/slices/modalSlice';

interface ReportItemProps {
  report: Report;
  isOwner: boolean;
}

const ReportItem: FC<ReportItemProps> = ({ report, isOwner }) => {
  const dispatch = useAppDispatch();
  const handleOpenModal = () => {
    dispatch(
      openModal({
        type: 'appReports',
        extra: { report, isOwner },
      }),
    );
  };

  const statusClass = classNames({
    'text-warning': report.reportStatus === 'WAITING',
    'text-danger': report.reportStatus === 'REJECTED',
    'text-success': report.reportStatus === 'APPROVED',
  });

  return (
    <Row
      key={report.contractId}
      className="w-100 align-items-center"
      onClick={handleOpenModal}
      style={{ cursor: 'pointer' }}
    >
      <Col xs={4} sm={8}>
        <p className="text-truncate">{report.title}</p>
      </Col>
      <Col xs={4} sm={2}>
        <p>{report.reportType}</p>
      </Col>
      <Col xs={4} sm={2}>
        <p className={statusClass}>{report.reportStatus}</p>
      </Col>
    </Row>
  );
};

export default ReportItem;
