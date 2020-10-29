import React from 'react';
import TablePagination from "@material-ui/core/TablePagination";

export function TablePageController(props) {
  return (
    <TablePagination
      rowsPerPageOptions={[5, 10, 50]}
      component="div"
      count={props.count}
      rowsPerPage={props.rowsPerPage}
      page={props.page}
      onChangePage={props.handleChangePage}
      onChangeRowsPerPage={props.handleChangeRowsPerPage}
    />
  );
}