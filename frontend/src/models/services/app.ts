export interface App {
  id: string;
  ownerId: string;
  contractId: string | null;
  appName: string;
  appDescription: string;
  featurePrice: number;
  bugPrice: number;
  available: boolean;
  iconImage: string;
  downloadLink: string;
}

export interface AddApp {
  appName: string;
  appDescription: string;
  featurePrice: string;
  bugPrice: string;
  available: boolean;
  iconImage: string;
  downloadLink: string;
}
