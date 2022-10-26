import React, { FC, useState } from 'react';
import { Button, Modal, ButtonGroup, Form } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import { useAppDispatch, useAppSelector } from '../../../hooks/defaultHooks';
import { modalExtra } from '../../../selectors/selectors';
import * as yup from 'yup';
import {
  Report,
  ReportType,
  useApproveReportMutation,
  useGetCurrentReportsQuery,
  useRejectReportMutation,
  useCreateReportMutation
} from '../../../store/api/reportsApiSlice/reportsApiSlice';
import { closeModal } from '../../../store/slices/modalSlice';
import { isFetchBaseQueryError } from '../../../utils/helpers';
import ReportForm from './reportForm';
import {useFormik} from "formik";

export interface ReportsExtra {
  type: ReportType;
  header?: string;
  contractId?: string;
  report?: Report;
  isOwner?: boolean;
}

const AppReports: FC = () => {
  const [createReport] = useCreateReportMutation();
  const { type, header, report, contractId, isOwner } = useAppSelector(modalExtra) as unknown as ReportsExtra;
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
  const initialValues = {
    title: '',
    description: '',
  };
  const validationSchema = yup
      .object()
      .strict()
      .shape({
        title: yup
            .string()
            .min(3, t('formErrors.reportTitle'))
            .max(255, t('formErrors.reportTitle'))
            .required(t('formErrors.required')),
        description: yup
            .string()
            .min(3, t('formErrors.reportDescription'))
            .max(5000, t('formErrors.reportDescription'))
            .required(t('formErrors.required')),
      });
  const formik = useFormik({
    initialValues,
    validationSchema,
    validateOnBlur: false,
    onSubmit: async ({ title, description }) => {
      const report = { title, description, contractId };
      try {
        await createReport({ report, type }).unwrap();
        handleClose();
        toast.success(t('modals.successReport'));
      } catch (error) {
        if (isFetchBaseQueryError(error)) {
          console.log(error);
        }
      }
    },
  });
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
      <Modal.Header closeVariant={'dark'} closeButton>
        <Modal.Title>{header}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={formik.handleSubmit}>
          <Form.Group className="position-relative">
            <Form.Control
              id="title"
              name="title"
              onChange={formik.handleChange}
              placeholder={t('modals.reportTitle')}
              value={formik.values.title}
              isInvalid={!!formik.errors.title}
            />
            <Form.Control.Feedback type="invalid" tooltip>
              {formik.errors.title}
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group className="position-relative my-3">
            <Form.Control
              as="textarea"
              name="description"
              style={{ resize: 'none' }}
              rows={4}
              id="description"
              onChange={formik.handleChange}
              placeholder={t('modals.reportDescription')}
              value={formik.values.description}
              isInvalid={!!formik.errors.description}
            />
            <Form.Control.Feedback type="invalid" tooltip>
              {formik.errors.description}
            </Form.Control.Feedback>
          </Form.Group>
          <ButtonGroup className="mt-2 justify-content-end">
            <Button onClick={handleClose} variant="danger">
              {t('modals.close')}
            </Button>
            <Button className="ms-2" type="submit">
              {t('modals.send')}
            </Button>
          </ButtonGroup>
        </Form>
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
