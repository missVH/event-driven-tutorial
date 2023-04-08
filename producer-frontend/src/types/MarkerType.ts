export interface MarkerType {
    markerId: number;
    longitude: string;
    latitude: string;
}

export const MarkerType = (markerId: number, longitude: string, latitude: string) => {
    return {markerId, longitude, latitude}
}