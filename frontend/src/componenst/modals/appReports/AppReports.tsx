import React, { FC, useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import { useAppDispatch, useAppSelector } from '../../../hooks/defaultHooks';
import { modalExtra } from '../../../selectors/selectors';
import {
  Report,
  ReportType,
  useApproveReportMutation,
  useGetCurrentReportsQuery,
  useRejectReportMutation,
} from '../../../store/api/reportsApiSlice/reportsApiSlice';
import { closeModal } from '../../../store/slices/modalSlice';
import { isFetchBaseQueryError } from '../../../utils/helpers';
import ReportForm from './reportForm';

export interface ReportsExtra {
  type?: ReportType;
  header?: string;
  contractId?: string;
  report?: Report;
  isOwner?: boolean;
}

const AppReports: FC = () => {
  const { type, header, report, isOwner } = useAppSelector(modalExtra) as unknown as ReportsExtra;
  const [isEditing, setEditing] = useState<boolean>(false);
  const { data } = useGetCurrentReportsQuery(report?.id as string, {
    skip: !!type,
  });
  const [approveReport] = useApproveReportMutation();
  const [rejectReport] = useRejectReportMutation();
  const dispatch = useAppDispatch();
  const { t } = useTranslation();
  const handleClose = () => {
    dispatch(closeModal());
  };
  const handleEdit = () => {
    setEditing(true);
  };

  const handleApprove = async () => {
    try {
      await approveReport(report?.id as string).unwrap();
      toast.success(t('toast.reportApproved'));
      dispatch(closeModal());
    } catch (error) {
      if (isFetchBaseQueryError(error)) {
        console.log(error);
      }
    }
  };

  const handleReject = async () => {
    try {
      await rejectReport(report?.id as string).unwrap();
      toast.success(t('toast.reportRejected'));
      dispatch(closeModal());
    } catch (error) {
      if (isFetchBaseQueryError(error)) {
        console.log(error);
      }
    }
  };

  const isLookingReport =
    report?.reportStatus === 'APPROVED' || report?.reportStatus === 'REJECTED';
  const isApprovedReport = report?.reportStatus === 'APPROVED';
  console.log(isEditing);
  return (
    <>
      <Modal.Header closeButton>
        <Modal.Title>
          {type ? header : isEditing ? t('modals.editReport') : data?.title}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {type || isEditing ? <ReportForm data={data} isEditing={isEditing} /> : data?.description}
      </Modal.Body>
      {!type && !isOwner && !isEditing && (
        <Modal.Footer>
          <Button disabled={isApprovedReport} onClick={handleEdit}>
            {t('modals.edit')}
          </Button>
          <Button onClick={handleClose}>{t('modals.close')}</Button>
        </Modal.Footer>
      )}
      {!type && isOwner && (
        <Modal.Footer>
          <Button disabled={isLookingReport} onClick={handleApprove}>
            {t('modals.approveReport')}
          </Button>
          <Button disabled={isLookingReport} onClick={handleReject}>
            {t('modals.rejectReport')}
          </Button>
          <Button onClick={handleClose}>{t('modals.close')}</Button>
        </Modal.Footer>
      )}
    </>
  );
};

export default AppReports;
