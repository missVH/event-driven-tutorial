export interface MessageType {
    timestamp: string;
}

export const MessageType = (timestamp: string) => {
    return {timestamp}
}