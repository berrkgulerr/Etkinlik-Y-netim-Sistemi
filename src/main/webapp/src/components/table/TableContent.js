import React from 'react';
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import CreateIcon from '@material-ui/icons/Create';
import DeleteIcon from "@material-ui/icons/Delete"
import IconButton from "@material-ui/core/IconButton";
import AddIcon from '@material-ui/icons/Add';
import VisibilityIcon from '@material-ui/icons/Visibility';
import LocationOnIcon from '@material-ui/icons/LocationOn';

export default function TableContent(props) {

  let iconMap = {
    "update": <CreateIcon/>,
    "delete": <DeleteIcon/>,
    "addGuest": <AddIcon/>,
    "showGuest" : <VisibilityIcon/>,
    "showLocation" : <LocationOnIcon/>
  }


  return (
    <TableBody>
      {getRowSlice().map(row => createTableRow(row))}
    </TableBody>

  );

  function getRowSlice() {
    return props
      .rows
      .slice(calculatePageBeginning(), calculatePageEnd());
  }

  function calculatePageBeginning() {
    return props.page * props.rowsPerPage;
  }

  function calculatePageEnd() {
    return props.page * props.rowsPerPage + props.rowsPerPage;
  }


  function createTableRow(row) {
    return (
      <TableRow hover role="checkbox" key={row.eventId}>
        {props.columns.map(column => createTableCell(column, row))}
      </TableRow>
    );
  }

  function createTableCell(column, row) {

    let cellValue = row[column.id];
    if (column.id === "update" || column.id === "delete" || column.id === "addGuest" || column.id === "showGuest" || column.id === "showLocation") {
      cellValue = createIcon(column.id, column.onClick, row.eventId);
    }

    return (
      <TableCell key={column.id} align={column.align}>
        {cellValue}
      </TableCell>
    );
  }

  function createIcon(key, onClick, eventId) {
    return (
      <IconButton aria-label={key} color="primary" onClick={() => onClick(eventId)}>
        {iconMap[key]}
      </IconButton>
    )
  }

}