import React, { FC } from 'react';
import { Dropdown } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useAppDispatch } from '../../hooks/defaultHooks';
import { openModal } from '../../store/slices/modalSlice';

interface ReportSectionProps {
  contractId: string;
}

const ReportsSection: FC<ReportSectionProps> = ({ contractId }) => {
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  const handleBugsReport = () => {
    dispatch(
      openModal({
        type: 'appReports',
        extra: { type: 'bug', header: t('modals.bugTitle'), contractId },
      }),
    );
  };
  const handleFeatureReport = () => {
    dispatch(
      openModal({
        type: 'appReports',
        extra: { type: 'feature', header: t('modals.featureTitle'), contractId },
      }),
    );
  };
  const handleClaimReport = () => {
    dispatch(
      openModal({
        type: 'appReports',
        extra: { type: 'claim', header: t('modals.claimTitle'), contractId },
      }),
    );
  };
  return (
    <>
      <Dropdown>
        <Dropdown.Toggle variant="outline-light">{t('app.reports')}</Dropdown.Toggle>
        <Dropdown.Menu>
          <Dropdown.Item onClick={handleFeatureReport}>{t('app.suggestFeature')}</Dropdown.Item>
          <Dropdown.Item onClick={handleBugsReport}>{t('app.suggestBug')}</Dropdown.Item>
          <Dropdown.Item onClick={handleClaimReport}>{t('app.claim')}</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </>
  );
};

export default ReportsSection;
