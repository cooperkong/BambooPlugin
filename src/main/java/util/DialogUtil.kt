package util

import java.awt.Component
import javax.swing.JOptionPane

fun showSuccessDialog(component: Component) = JOptionPane.showMessageDialog(component,
                                                            "Connection successful.",
                                                            "Info",
                                                            JOptionPane.PLAIN_MESSAGE)