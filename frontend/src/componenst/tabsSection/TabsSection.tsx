import React, { FC } from 'react';
import { useTranslation } from 'react-i18next';
import { Tabs, Tab } from 'react-bootstrap';
import { App } from '../../models/services/app';

interface TabsSectionProps {
  data: App | undefined;
}

const TabsSection: FC<TabsSectionProps> = ({ data }) => {
  const { t } = useTranslation();
  return (
    <Tabs defaultActiveKey="description" className="mt-5" id="justify-tab-example" justify>
      <Tab eventKey="description" title={t('app.description')} className="mt-2 overflow-hidden">
        {data?.appDescription}
      </Tab>
      <Tab eventKey="comments" title={t('app.comments')} className="mt-3">
        Comments...
      </Tab>
    </Tabs>
  );
};

export default TabsSection;
