import { useState, useEffect } from "react";
import { ISupervisorData } from "../models/interfaces";
import "./Form.css";

function Form() {
  const [state, setState] = useState({
    data: null,
    supervisorList: [""],
    displayIndex: 0,
    formData: {
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      supervisor: ""
    },
    formErrors: {
      firstName: "",
      lastName: "",
      phoneNumber: "",
      supervisor: ""
    },
    formSubmitSuccess: false
  });

  const formSubmitSuccessString = "Successfully submitted!";

  const createSupervisorList = (data: Array<ISupervisorData>) => {
    const returnValue: string[] = [];
    data.map((supervisor) => {
      returnValue.push(
        supervisor.jurisdiction +
          " - " +
          supervisor.lastName +
          ", " +
          supervisor.firstName
      );
    });
    return returnValue;
  };

  // populate initial values
  useEffect(() => {
    const fetchData = async () => {
      setState({ ...state, data: null });
      fetch("http://localhost:8080/api/supervisors", {
        method: "GET",
        mode: "cors"
      })
        .then((response) => response.json())
        .then((data) => {
          setState((prevState) => ({ ...prevState, data: data }));
          const newSupervisorList = createSupervisorList(data);
          setState((prevState) => ({
            ...prevState,
            supervisorList: newSupervisorList
          }));
          if (!ignore) {
            setState((prevState) => ({ ...prevState, data: data }));
          }
        })
        .catch((err) => {
          console.log(err.message);
        });
    };
    let ignore = false;
    fetchData();
    return () => {
      ignore = true;
    };
  }, []);

  // POST must have have a valid supervisor value, even if user never sets a value for supervisor
  useEffect(() => {
    setState((prevState) => ({
      ...prevState,
      formData: {
        ...prevState.formData,
        supervisor: state.supervisorList[state.displayIndex]
      }
    }));
  }, [state.supervisorList, state.displayIndex]);

  const clearFormData = () => {
    ["firstName", "lastName", "phoneNumber", "supervisor"].forEach((str) => {
      setState((prevState) => ({
        ...prevState,
        formErrors: {
          ...prevState.formErrors,
          [str]: ""
        }
      }));
    });
    setState((prevState) => ({
      ...prevState,
      formSubmitSuccess: false
    }));
  };

  const postData = async () => {
    clearFormData();
    fetch("http://localhost:8080/api/submit", {
      method: "POST",
      mode: "cors",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(state.formData)
    })
      .then((response) => {
        if (response.status === 200) {
          setState((prevState) => ({
            ...prevState,
            formSubmitSuccess: true
          }));
        }
        return response.json();
      })
      .then((data) => {
        if (data.apierror && data.apierror.subErrors) {
          data.apierror.subErrors.forEach((subError) => {
            setState((prevState) => ({
              ...prevState,
              formErrors: {
                ...prevState.formErrors,
                [subError.field]: subError.message
              }
            }));
          });
        }
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  function handleSupervisorChange(event) {
    let myArray: Array<string> = [];

    if (state.supervisorList) {
      myArray = state.supervisorList;
    }

    if (myArray) {
      myArray.forEach((element, index: number) => {
        if (element === event.target.value) {
          setState({ ...state, displayIndex: index });
        }
      });
    }
  }

  const handleChange = (event) => {
    const { name, value } = event.target;
    setState((prevState) => ({
      ...prevState,
      formData: {
        ...prevState.formData,
        [name]: value
      }
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setState((prevState) => ({
      ...prevState,
      formData: {
        ...prevState.formData,
        supervisor: state.supervisorList[state.displayIndex]
      }
    }));

    postData();
  };

  return (
    state.data && (
      <>
        <h2 className="formTitle">Notification Form</h2>
        <form className="form" onSubmit={handleSubmit}>
          <div className="formError1">
            {state.formErrors && state.formErrors.firstName ? (
              <span>{"* " + state.formErrors.firstName + " *"}</span>
            ) : null}
            {state.formErrors && state.formErrors.lastName ? (
              <span>{"* " + state.formErrors.lastName + " *"}</span>
            ) : null}
          </div>
          <div>
            <label>
              First Name
              <input
                type="text"
                name="firstName"
                value={state.formData.firstName}
                onChange={handleChange}
              />
            </label>
            <label>
              Last Name
              <input
                type="text"
                name="lastName"
                value={state.formData.lastName}
                onChange={handleChange}
              />
            </label>
          </div>
          <div className="formError1">
            {state.formErrors && state.formErrors.phoneNumber ? (
              <>
                <span></span>
                <span>{"* " + state.formErrors.phoneNumber + " *"}</span>
              </>
            ) : null}
          </div>

          <div>
            <label>
              Email
              <input
                type="text"
                name="email"
                value={state.formData.email}
                onChange={handleChange}
              />
            </label>
            <label>
              Phone Number
              <input
                type="text"
                name="phoneNumber"
                value={state.formData.phoneNumber}
                onChange={handleChange}
              />
            </label>
          </div>
          <div>
            <label>
              Supervisor
              <select name="supervisor" onChange={handleSupervisorChange}>
                {state.supervisorList &&
                  state.supervisorList.map((el: string, index: number) => (
                    <option
                      defaultValue={el}
                      selected={index === state.displayIndex}
                      key={index}
                    >
                      {el}
                    </option>
                  ))}
              </select>
            </label>
          </div>
          <div className="formError2">
            {state.formErrors && state.formErrors.supervisor ? (
              <div>{"* " + state.formErrors.supervisor + " *"}</div>
            ) : null}
          </div>
          <button type="submit">Send</button>
          {state.formSubmitSuccess ? (
            <div className="success">{"* " + formSubmitSuccessString + " *"}</div>
          ) : null}
        </form>
      </>
    )
  );
}

export default Form;
