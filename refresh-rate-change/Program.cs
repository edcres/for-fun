using System.Runtime.InteropServices;
using Microsoft.Win32;

namespace DisplaySettingsListener
{
    class Program
    {

        [DllImport("user32.dll")]
        public static extern bool EnumDisplaySettings(string deviceName, int modeNum, ref DEVMODE devMode);

        [StructLayout(LayoutKind.Sequential)]
        public struct DEVMODE
        {
            private const int CCHDEVICENAME = 32;
            private const int CCHFORMNAME = 32;
            [MarshalAs(UnmanagedType.ByValTStr, SizeConst = CCHDEVICENAME)]
            public string dmDeviceName;
            public short dmSpecVersion;
            public short dmDriverVersion;
            public short dmSize;
            public short dmDriverExtra;
            public int dmFields;
            public int dmPositionX;
            public int dmPositionY;
            public int dmDisplayOrientation;
            public int dmDisplayFixedOutput;
            public short dmColor;
            public short dmDuplex;
            public short dmYResolution;
            public short dmTTOption;
            public short dmCollate;
            [MarshalAs(UnmanagedType.ByValTStr, SizeConst = CCHFORMNAME)]
            public string dmFormName;
            public short dmLogPixels;
            public int dmBitsPerPel;
            public int dmPelsWidth;
            public int dmPelsHeight;
            public int dmDisplayFlags;
            public int dmDisplayFrequency;
            public int dmICMMethod;
            public int dmICMIntent;
            public int dmMediaType;
            public int dmDitherType;
            public int dmReserved1;
            public int dmReserved2;
            public int dmPanningWidth;
            public int dmPanningHeight;
        }

        static void Main(string[] args)
        {
            Console.WriteLine("Listening for display settings changes...");
            SystemEvents.DisplaySettingsChanged += OnDisplaySettingsChanged;

            // Prevents the application from exiting immediately
            Console.WriteLine("Press 'Enter' to exit.");
            Console.ReadLine();
        }

        private static void OnDisplaySettingsChanged(object sender, EventArgs e)
        {
            // This is where you could add code to check and adjust the refresh rate if needed
            Console.WriteLine("Display settings changed.");
            CheckRefreshRate();
        }

        private static void CheckRefreshRate()
        {
            DEVMODE devMode = new DEVMODE();
            devMode.dmSize = (short)Marshal.SizeOf(typeof(DEVMODE));

            if (EnumDisplaySettings(null, -1, ref devMode))
            {
                Console.WriteLine($"Current Refresh Rate: {devMode.dmDisplayFrequency}Hz");
                if (devMode.dmDisplayFrequency == 144)
                {
                    Console.WriteLine("The refresh rate is 144Hz.");
                }
                else if (devMode.dmDisplayFrequency == 60)
                {
                    Console.WriteLine("The refresh rate is 60Hz.");
                }
                else
                {
                    Console.WriteLine("The refresh rate is neither 144Hz nor 60Hz.");
                }
            }
            else
            {
                Console.WriteLine("Unable to enumerate display settings.");
            }
        }
    }
}
