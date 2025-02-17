import React, { useState,useEffect } from "react";
import { Container, Paper, Box, TextField, Button} from "@mui/material";

const paperStyle ={
    padding:'50px 20px',width:600,margin:'20px auto'}
export default function Student() {

      const[name,setName]=useState('')
      const[address,setAddress]=useState('')
      const[students, setStudents]=useState([]);
    const handleClick = (e) => {
        e.preventDefault();

        // Create student object
        const student = {name, address};

        console.log("Sending student data:", student);

        fetch("http://localhost:8080/api/student", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(student),
        })
            .then(() => {
                console.log("New Student added");
                fetchStudents();
            })
            .catch((error) => console.error("Error adding student:", error));
    };

    const fetchStudents = () => {
        fetch("http://localhost:8080/api/student")
            .then((response) => response.json())
            .then((data) => {
                if (Array.isArray(data)) {
                    setStudents(data); // Ensure data is an array
                } else {
                    console.error("Fetched data is not an array:", data);
                }
            })
            .catch((error) => console.error("Error fetching students:", error));
    };

    useEffect(()=>{
       fetch("http://localhost:8080/api/student")
           .then((response) => response.json())
           .then((data) => setStudents(data))
           .catch((error) => console.error("Error fetching students:", error));
    }, []);

    return (
        <Container>
            <Paper elevation={3} style={paperStyle}>
                <h1 style={{color:"blue"}}><u>Add Student</u></h1>
                <Box
            component="form"
            sx={{ '& > :not(style)': { m: 1, width: '25ch' } }}
            noValidate
            autoComplete="off"
            >
            <TextField id="outlined-basic" label="Student Name" variant="outlined" fullWidth
            value={name}
            onChange={(e)=>setName(e.target.value)}/>
            <TextField id="outlined-basic" label="Student Address" variant="outlined" fullWidth
                       value={address}
                       onChange={(e)=>setAddress(e.target.value)}/>
                        <Button variant="contained" color="secondary" onClick={handleClick} sx={{ marginTop: 2 }}>
                            Submit
                        </Button>
                    </Box>
            </Paper>
            <h1>Students</h1>

            <Paper elevation={3} style={paperStyle}>
                {students && Array.isArray(students) ? (
                    students.map((student) => (
                        <Paper
                            elevation={6}
                            style={{ margin: "10px", padding: "15px", textAlign: "left" }}
                            key={student.id}
                        >
                            Id: {student.id}<br />
                            Name: {student.name}<br />
                            Address: {student.address}
                        </Paper>
                    ))
                ) : (
                    <p>No students available</p>
                )}
            </Paper>


        </Container>
        );
}
