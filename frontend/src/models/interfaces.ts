export interface ISupervisorData {
  firstName: string,
  lastName: string,
  jurisdiction: string
}

export interface ISupervisorSubmit {
  firstName?: string,
  lastName?: string,
  email?: string,
  phoneNumber?: string,
  supervisor?: string
}

// TODO: create interface for apierror and suberrors