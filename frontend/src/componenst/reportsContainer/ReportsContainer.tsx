import React, { FC } from 'react';
import { useTranslation } from 'react-i18next';
import {
  useGetAppReportsQuery,
  useGetContractReportsQuery,
} from '../../store/api/reportsApiSlice/reportsApiSlice';
import ReportItem from '../reportItem';
import ReportsContainerHeader from './reportsContainerHeader';

interface ReportsTabProps {
  isOwner: boolean;
  appId?: string | undefined;
  contractId: string | undefined;
}

const ReportsContainer: FC<ReportsTabProps> = ({ isOwner, appId, contractId }) => {
  const { t } = useTranslation();

  const { data: dataForOwner, isLoading: isOwnerDataLoading } = useGetAppReportsQuery(
    appId as string,
    {
      skip: !isOwner,
    },
  );
  const { data: dataForQa, isLoading: isQaDataLoading } = useGetContractReportsQuery(
    contractId as string,
    {
      skip: isOwner,
    },
  );

  return (
    <div className="d-flex flex-column gap-3 justify-content-center">
      <ReportsContainerHeader />
      {isOwner
        ? !isOwnerDataLoading && dataForOwner && dataForOwner?.length > 0
          ? dataForOwner?.map((report) => (
              <ReportItem isOwner={isOwner} report={report} key={report.id} />
            ))
          : t('reports.noReportsYet')
        : !isQaDataLoading && dataForQa && dataForQa.length > 0
        ? dataForQa?.map((report) => (
            <ReportItem isOwner={isOwner} report={report} key={report.id} />
          ))
        : t('reports.noReportsYet')}
    </div>
  );
};

export default ReportsContainer;
