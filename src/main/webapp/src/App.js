import React, {Component} from 'react';
import PaginationTable from "./components/table/PaginationTable";
import Button from "@material-ui/core/Button";
import PlusIcon from '@material-ui/icons/Add';
import axios from "axios";
import Snackbar from "@material-ui/core/Snackbar";
import Alert from "@material-ui/lab/Alert";
import ReactDialog from "./components/common/ReactDialog";
import ReactDialogUpdate from "./components/common/ReactDialogUpdate";

class App extends Component {

  eventDialogFields = [
      {id: "eventId", label: "Event ID", type: "text", defaultValue: ""},
      {id: "title", label: "Title", type: "text", defaultValue: ""},
      {id: "startDate", label: "Start Date",type:"datetime-local", defaultValue: ""},
      {id: "endDate", label: "End Date", type: "datetime-local", defaultValue: ""},
      {id: "location", label: "Location", type: "text", defaultValue: ""},
      {id: "quota", label: "Quota", type: "number", defaultValue: ""},
  ]

    guestDialogFields = [
        {id: "name", label: "Name", type: "text", defaultValue: ""},
        {id: "surname", label: "Surname", type: "text", defaultValue: ""},
        {id: "tcKimlikNo", label: "TC Kimlik No",type:"text", defaultValue: ""},
        {id: "email", label: "E-Mail", type: "email", defaultValue: ""},
    ]


    eventDialogFieldsUpdate=()=>{
        return [
            {id: "title", label: "Title", type: "text"},
            {id: "startDate", label: "Start Date", type: "datetime-local"},
            {id: "endDate", label: "End Date", type: "datetime-local"},
            {id: "location", label: "Location", type: "text"},
            {id: "quota", label: "Quota", type: "number"},
        ];
    }

  constructor() {
        super();
        this.state = {
            rows: [],
            updatedEvent:[],
            addEventModalOpen: false,
            updateEventModalOpen: false,
            addGuestModalOpen : false,
            snackbarProperties: {
            isOpen: false,
            message: "",
            severity: "",
            },
            temp : 0,
            temp1 : 0
        }
  }

  componentDidMount() {
    axios.get("/events")
      .then(response => {
        this.setState({rows: response.data})
      })
  }

  handleUpdateDate = (x) => {
      let index = 10;
      if(x.startDate){
          let s = x.startDate.toString();
          s = s.substring(0, index) + ' ' + s.substring(index + 1);
          x.startDate = s;
      }
      else{
          return  this.snackbarOpen("Give valid Start Date","error")
      }
      if (x.endDate){
          let e = x.endDate.toString();
          e = e.substring(0, index) + ' ' + e.substring(index + 1);
          x.endDate = e;
      }
      else{
          return this.snackbarOpen("Give valid End Date","error")

      }
      x.eventId = this.state.temp
  }

  handleDate = (x) => {
      let index = 10;
      if(x.startDate){
          let s = x.startDate.toString();
          s = s.substring(0, index) + ' ' + s.substring(index + 1);
          x.startDate = s;
      }
      else{
          return this.snackbarOpen("Give valid Start Date","error")
      }
      if (x.endDate){
          let e = x.endDate.toString();
          e = e.substring(0, index) + ' ' + e.substring(index + 1);
          x.endDate = e;
      }
      else{
          return this.snackbarOpen("Give valid End Date","error")
      }
    }






  toggleAddEventModal = () => {
    this.setState({addEventModalOpen: !this.state.addEventModalOpen})
  }


  toggleAddGuestModal = (e) =>{
      this.setState({addGuestModalOpen : !this.state.addGuestModalOpen}
      )
      this.setState({temp1 : e})
  }


  toggleUpdateEventModal = async (e) => {
      this.setState({updateEventModalOpen: !this.state.updateEventModalOpen})
      await axios.get("/events/"+e)
          .then(response =>{
              this.setState({
                  updatedEvent : response.data
              })
          })
      if (Date.parse(new Date()) < Date.parse(this.state.updatedEvent.startDate)){
          this.setState({
              temp: e
          })
      }

      else {
          this.setState({updateEventModalOpen: !this.state.updateEventModalOpen})
          this.snackbarOpen("Start Date Has Already Passed, Can't Update!", "error");
      }
  }






  isValid = (e) =>{
      if(e.eventId === "" || e.eventId === null){
          this.snackbarOpen("Please give valid event ID", "error")
          return false;
      }
      else if(e.title === "" || e.title === null){
          this.snackbarOpen("Please give valid Title", "error")
          return false;
      }
      else if(e.startDate === "" || e.startDate === null){
          this.snackbarOpen("Please give valid Start Date", "error")
          return false;
      }
      else if(Date.parse(new Date()) > Date.parse(e.startDate)){
          this.snackbarOpen("Star Date can't be in the past", "error")
          return false;
      }
      else if(e.endDate === ""|| e.endDate === null){
          this.snackbarOpen("Please give valid End Date", "error")
          return false;
      }
      else if(Date.parse(e.startDate)> Date.parse(e.endDate)){
          this.snackbarOpen("Start Date can't be greater than End Date", "error")
          return false
      }
      else if(e.location === "" || e.location === null){
          this.snackbarOpen("Please give valid Location", "error")
          return false;
      }
      else if(e.quota === "" || e.quota === null){
          this.snackbarOpen("Please give valid Quota", "error")
          return false;
      }
      else if(e.quota < 1){
          this.snackbarOpen("Quota must be greater than 0", "error")
          return false;
      }
      else return true;
  }


  submitEventAdd = (inputData) => {
      if(this.isValid(inputData)) {
          this.handleDate(inputData);
          axios.post("/events", inputData)
              .then(response => {
                  this.setState(prevState => (
                      {rows: [...prevState.rows, response.data]}
                  ));
                  this.snackbarOpen("Event with id: " + inputData.eventId + " has been added successfully!", "success");
                  this.toggleAddEventModal();
              })
              .catch(error => {
                  if(error.response.data.message === "could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"){
                      this.snackbarOpen("Give Valid Event ID", "error")
                  }
                  else if(error.response.data.message === "HV000090: Unable to access isQuotaValid."){
                      this.snackbarOpen("Give Valid Quota", "error")
                  }
                  else if(error.response.data.message === "HV000090: Unable to access isDatesValid."){
                      this.snackbarOpen("Give Valid Dates", "error")
                  }
                  else if (error.response.data.errors[0] && error.response.data.errors[0].field === "eventId") {
                      this.snackbarOpen("Give Valid Event ID", "error")
                  }
                  else if (error.response.data.errors[0] && error.response.data.errors[0].field === "title") {
                      this.snackbarOpen("Give Valid Title", "error")
                  }
                  else if (error.response.data.errors[0] && error.response.data.errors[0].field === "location") {
                      this.snackbarOpen("Give Valid Location", "error")
                  }
                  else if(error.response.status === 500) {
                      this.snackbarOpen("This event id has already been taken. Please give another one", "error")
                  }
              })
      }
  }





  submitUpdatedEvent = (inputData) => {
      if(this.isValid(inputData)) {
          this.handleUpdateDate(inputData);
          axios.put("/events/" + this.state.temp, inputData)
              .then(response => {
                  this.setState(prevState => ({
                      rows: this.state.rows.map(event => event.eventId === inputData.eventId ? response.data : event),
                      updateEventModalOpen: !this.state.updateEventModalOpen
                  }));
                  this.snackbarOpen("Event has been updated successfully!", "success");
              })
              .catch(error => {

                  if(error.response.data.message === "HV000090: Unable to access isQuotaValid."){
                      this.snackbarOpen("Give Valid Quota", "error")
                  }
                  else if(error.response.data.message === "HV000090: Unable to access isDatesValid."){
                      this.snackbarOpen("Give Valid Dates", "error")
                  }
                  else if (error.response.data.errors[0].field === "title") {
                      this.snackbarOpen("Give Valid Title", "error")
                  }
                  else if (error.response.data.errors[0].field === "location") {
                      this.snackbarOpen("Give Valid Location", "error")
                  }
                  else if(error.response.status === 500) {
                      this.snackbarOpen("This event id has already been taken. Please give another one", "error")
                  }
              })
      }
  }







  onEventDelete = (eventId) => {
    axios.delete("/events/" + eventId)
        .then(response => {
            this.setState( {
                rows: this.state.rows.filter((event) => event.eventId !== eventId)
            })
            this.snackbarOpen("Event with id \"" + eventId + "\" has been deleted!", "success")
        })
        .catch(error => {
            this.snackbarOpen("Start Date Has Already Passed, Can't Delete!", "error")

        })
  }





  snackbarOpen = (message, severity) => {
    this.setState(prevState => {
      let snackbarProperties = {...prevState.snackbarProperties}
      snackbarProperties.isOpen = true;
      snackbarProperties.message = message;
      snackbarProperties.severity = severity;
      return {snackbarProperties};
    })
  }

  snackbarClose = () => {
    this.setState(prevState => {
      let snackbarProperties = {...prevState.snackbarProperties}
      snackbarProperties.isOpen = false;
      snackbarProperties.message = "";
      snackbarProperties.severity = "";
      return {snackbarProperties};
    })
  }

  submitGuest = async (inputData) => {
      if(this.tcno(inputData.tcKimlikNo)) {
          if(this.emailValid(inputData.email)) {
              await axios.post("/events/" + this.state.temp1 + "/guests", inputData)
                  .then(response => {
                      this.setState({addGuestModalOpen: !this.state.addGuestModalOpen})
                      this.snackbarOpen("Guest has been added successfully!", "success");
                  })
                  .catch(error => {
                      if (error.response.status === 400) {
                          if(error.response.data.errors[0].field === "surname"){
                              this.snackbarOpen("Give Valid Surname", "error")
                          }
                          if(error.response.data.errors[0].field === "name"){
                              this.snackbarOpen("Give Valid Name", "error")
                          }
                      }
                      else if(error.response.data.message === "Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Error while committing the transaction"){
                          this.snackbarOpen("Start Date Has Already Passed, Can't Add New Guest", "error")
                      }
                      else if(error.response.data.message === "could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"){
                          this.snackbarOpen("TC Kimlik No or E-Mail Has Already Been Used", "error")
                      }
                      else if(error.response.data.message === "quota is full"){
                          this.snackbarOpen("Quota is full", "error")
                      }
                  })
          }
      }
  }


    showGuest =  (e) => {
        axios.get("/events/"+e+"/guests")
            .then(response =>{
                console.log(response.data)
                this.setState({updatedEvent : response.data})
            })
        return this.state.updatedEvent
    }

    showLocation =  (e) => {
        axios.get("/events/"+e)
            .then(response =>{
                this.setState({updatedEvent : response.data})
                window.location.assign('http://google.com/maps/search/'+this.state.updatedEvent.location);
            })

    }



  emailValid(value){
      if(/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/.test(value)){
          return true
      }
      else {
          this.snackbarOpen("Give Valid E-Mail", "error")
      }
  }


  tcno(value) {
        value = String(value);
        // T.C. identity number should have 11 digits and first should be non-zero.
        if (!(/^[1-9]\d{10}$/).test(value)){
            this.snackbarOpen("Give Valid Tc Kimlik No", "error")
            return false;
        }
        var digits = value.split(''),
            // store last 2 digits (10th and 11th) which are actually used for validation
            d10 = Number(digits[9]),
            d11 = Number(digits[10]),
            // we'll also need the sum of first 10 digits for validation
            sumOf10 = 0,
            evens = 0,
            odds = 0;
        digits.forEach(function (d, index) {
            d = Number(d);
            if (index < 10) sumOf10 += d;
            if (index < 9) {
                if ((index + 1) % 2 === 0) {
                    evens += d;
                } else {
                    odds += d;
                }
            }
        });
        // check if the unit-digit of the sum of first 10 digits equals to the 11th digit.
        if (sumOf10 % 10 !== d11){
            this.snackbarOpen("Give Valid Tc Kimlik No", "error")
            return false;
        }
        // check if unit-digit of the sum of odds * 7 and evens * 9 is equal to 10th digit.
        if (((odds * 7) + (evens * 9)) % 10 !== d10){
            this.snackbarOpen("Give Valid Tc Kimlik No", "error")
            return false;
        }
        // check if unit-digit of the sum of odds * 8 is equal to 11th digit.
        if ((odds * 8) % 10 !== d11){
            this.snackbarOpen("Give Valid Tc Kimlik No", "error")
            return false;
        }
        return true;
    }


  render() {
    return (
      <div className="App">
        <Button variant="contained"
                color="primary"
                style={{float: "right"}}
                onClick={this.toggleAddEventModal}
                startIcon={<PlusIcon/>}>
          Add Event
        </Button>
        <Snackbar open={this.state.snackbarProperties.isOpen} autoHideDuration={5000} onClose={this.snackbarClose}
                  anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
          <Alert onClose={this.snackbarClose} severity={this.state.snackbarProperties.severity}>
            {this.state.snackbarProperties.message}
          </Alert>
        </Snackbar>
        <ReactDialog fields={this.eventDialogFields} title="Add Event" isOpen={this.state.addEventModalOpen} onClose={this.toggleAddEventModal}
                     onSubmit={this.submitEventAdd}/>

        <ReactDialogUpdate fields={this.eventDialogFieldsUpdate(this.state.rows)} title="Update Event" isOpen={this.state.updateEventModalOpen} onClose={this.toggleUpdateEventModal}
                     onSubmit={this.submitUpdatedEvent}/>

        <ReactDialog fields={this.guestDialogFields} title="Add Guest" isOpen={this.state.addGuestModalOpen}  onClose={this.toggleAddGuestModal}
                     onSubmit={this.submitGuest}/>

        <PaginationTable rows={this.state.rows} onUpdate={this.toggleUpdateEventModal} onDelete={this.onEventDelete} onAddGuest={this.toggleAddGuestModal} onShowGuest={this.showGuest} onShowLocation={this.showLocation}/>
      </div>
    );
  }


}

export default App;
